package it.infotechconsults.bric48.backend.rest.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponseDTO extends BaseResponseDTO {
    private String message;
    private Instant sentAt;
    private UserResponseDTO receiver;
    private UserResponseDTO sender;
    // private NotiifcationResponseDTO notificationId;
}
