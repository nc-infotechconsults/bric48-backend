package it.infotechconsults.bric48.backend.rest.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationCodeDTO {
    private String title;
    private String type;
    private String value;
    private Set<NotificationTranslationDTO> translations;
}
