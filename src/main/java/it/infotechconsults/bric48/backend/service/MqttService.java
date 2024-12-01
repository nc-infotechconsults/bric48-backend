package it.infotechconsults.bric48.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MqttService implements MessageHandler {
    
    @Autowired
    private MessageChannel mqttOutboundChannel;

    @Autowired
    private MqttPahoMessageDrivenChannelAdapter mqttInbound;

    public void subscribeToTopic(String topic) {
        mqttInbound.addTopic(topic);
        System.out.println("Subscribed to topic: " + topic);
    }

    public void unsubscribeFromTopic(String topic) {
        mqttInbound.removeTopic(topic);
        System.out.println("Unsubscribed from topic: " + topic);
    }

    public void sendMessageToTopic(String topic, String message) {
        mqttOutboundChannel.send(
                MessageBuilder.withPayload(message)
                        .setHeader("mqtt_topic", topic) // Set topic dynamically
                        .build());
        log.debug("Message sent to topic [{}]: {}", topic, message);
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        log.debug("Message received: {}", message.toString());
    }
}
