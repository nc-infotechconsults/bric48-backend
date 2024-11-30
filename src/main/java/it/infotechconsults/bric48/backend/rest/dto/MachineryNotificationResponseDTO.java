package it.infotechconsults.bric48.backend.rest.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MachineryNotificationResponseDTO extends BaseResponseDTO{
    private String type;
    private String value;
    private String description;
    private MachineryResponseDTO machinery;
    private Boolean solved;
    private Instant createdAt;
}
