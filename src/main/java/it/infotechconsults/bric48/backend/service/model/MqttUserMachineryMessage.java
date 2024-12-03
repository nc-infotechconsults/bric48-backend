package it.infotechconsults.bric48.backend.service.model;

import lombok.Data;

@Data
public class MqttUserMachineryMessage {
    private String userId;
    private String machineryId;
}
