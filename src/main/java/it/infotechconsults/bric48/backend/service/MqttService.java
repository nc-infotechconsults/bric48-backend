package it.infotechconsults.bric48.backend.service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;

import it.infotechconsults.bric48.backend.configuration.MqttConfig;
import it.infotechconsults.bric48.backend.service.model.MqttEvent;
import it.infotechconsults.bric48.backend.service.model.MqttUserMachineryMessage;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MqttService implements ApplicationListener<MqttEvent> {

    @Autowired
    private MqttConfig.Gateway gateway;

    @Autowired
    private MqttPahoMessageDrivenChannelAdapter mqttInbound;

    @Getter
    private ConcurrentHashMap<String, Set<String>> userNearMachineries = new ConcurrentHashMap<>();

    @Value("${mqtt.broker.topic.user-near-machinery}")
    private String userNearMachineryTopic;

    @PostConstruct
    public void initTopic() {
        subscribeToTopic(userNearMachineryTopic);
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
    public void onApplicationEvent(MqttEvent event) {
        log.debug("Received mqtt event - {}", event.getMessage().toString());
        switch (event.getMessage().getHeaders().get("mqtt_receivedTopic").toString()) {
            case "user-machinery": {
                MqttUserMachineryMessage m = (MqttUserMachineryMessage) event.getMessage().getPayload();
                Set<String> userIds = new HashSet<>();
                if (userNearMachineries.containsKey(m.getMachineryId())) {
                    userIds = userNearMachineries.get(m.getMachineryId());
                } else {
                    userIds = new HashSet<>();
                }
                userIds.add(m.getUserId());
                userNearMachineries.put(m.getMachineryId(), userIds);
                break;
            }
            default:
                break;
        }
    }
}
