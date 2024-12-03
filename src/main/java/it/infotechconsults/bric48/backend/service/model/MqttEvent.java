package it.infotechconsults.bric48.backend.service.model;

import org.springframework.context.ApplicationEvent;
import org.springframework.messaging.Message;

import lombok.Getter;

@Getter
public class MqttEvent extends ApplicationEvent{
    private Message<?> message;

    public MqttEvent(Object source, Message<?> message){
        super(source);
        this.message = message;
    }
}
