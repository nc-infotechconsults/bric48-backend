package it.infotechconsults.bric48.backend.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MachineryNotificationDTO {
    private String type;
    private String value;
    private String code;
    private String description;
    private String mserial;
    private String isSolved;
    private String timestamp;
}
