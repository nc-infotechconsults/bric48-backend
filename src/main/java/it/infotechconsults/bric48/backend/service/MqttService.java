package it.infotechconsults.bric48.backend.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.infotechconsults.bric48.backend.configuration.MqttConfig;
import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.mapper.UserMapper;
import it.infotechconsults.bric48.backend.repository.UserRepository;
import it.infotechconsults.bric48.backend.rest.dto.UserResponseDTO;
import it.infotechconsults.bric48.backend.service.model.MqttEvent;
import it.infotechconsults.bric48.backend.service.model.MqttUserMachineryMessage;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MqttService implements ApplicationListener<MqttEvent> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MqttConfig.Gateway gateway;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MqttPahoMessageDrivenChannelAdapter mqttInbound;

    @Getter
    private ConcurrentHashMap<String, Set<UserResponseDTO>> userNearMachineries = new ConcurrentHashMap<>();

    @Value("${mqtt.broker.topic.user-near-machinery}")
    private String userNearMachineryTopic;

    @Value("${mqtt.broker.topic.user-far-machinery}")
    private String userFarMachineryTopic;

    @Value("${websocket.topic.machinery-users}")
    private String machineryUsers;

    @PostConstruct
    public void initTopic() {
        subscribeToTopic(userNearMachineryTopic);
        subscribeToTopic(userFarMachineryTopic);
    }

    public void subscribeToTopic(String topic) {
        mqttInbound.addTopic(topic);
        log.info("Subscribed to topic: {}", topic);
    }

    public void unsubscribeFromTopic(String topic) {
        mqttInbound.removeTopic(topic);
        log.info("Unsubscribed from topic: {}", topic);
    }

    public void sendMessageToTopic(String topic, String message) {
        gateway.sendToMqtt(message, topic);
        log.debug("Message sent to topic [{}]: {}", topic, message);
    }

    @Override
    @Transactional
    public void onApplicationEvent(MqttEvent event) {
        log.debug("Received mqtt event - {}", event.getMessage().toString());
        try {
            switch (event.getMessage().getHeaders().get("mqtt_receivedTopic").toString()) {
                case "user-near-machinery": {
                    MqttUserMachineryMessage m = objectMapper.readValue((String) event.getMessage().getPayload(),
                            MqttUserMachineryMessage.class);
                    Set<UserResponseDTO> userIds = new HashSet<>();
                    if (userNearMachineries.containsKey(m.getMachineryId())) {
                        userIds = userNearMachineries.get(m.getMachineryId());
                    } else {
                        userIds = new HashSet<>();
                    }
                    if (!userIds.stream().anyMatch(t -> t.getId().equals(m.getUserId()))) {
                        Optional<User> user = userRepository.findById(m.getUserId());
                        if (user.isPresent()) {
                            userIds.add(userMapper.entityToResponse(user.get()));
                            userNearMachineries.put(m.getMachineryId(), userIds);
                        }
                    }

                    simpMessagingTemplate.convertAndSend(machineryUsers, userNearMachineries);
                    break;
                }
                case "user-far-machinery": {
                    MqttUserMachineryMessage m = objectMapper.readValue((String) event.getMessage().getPayload(),
                            MqttUserMachineryMessage.class);
                    if (userNearMachineries.containsKey(m.getMachineryId())) {
                        Set<UserResponseDTO> userIds = userNearMachineries.get(m.getMachineryId()).stream()
                                .filter(x -> !x.getId().equals(m.getUserId())).collect(Collectors.toSet());
                        userNearMachineries.put(m.getMachineryId(), userIds);
                    }

                    simpMessagingTemplate.convertAndSend(machineryUsers, userNearMachineries);
                    break;
                }
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("Error occurred: ", e);
        }
    }
}
