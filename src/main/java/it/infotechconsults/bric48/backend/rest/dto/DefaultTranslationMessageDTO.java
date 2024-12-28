package it.infotechconsults.bric48.backend.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultTranslationMessageDTO {
    private String language;
    private Boolean isDefault;
    private String message;
}
