package it.infotechconsults.bric48.backend.rest.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationCodeResponseDTO extends AuditResponseDTO {
    private String title;
    private String code;
    private Set<NotificationTranslationResponseDTO> translations;
}
