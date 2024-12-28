package it.infotechconsults.bric48.backend.rest.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultMessageDTO {
    private String title;
    private Set<DefaultTranslationMessageDTO> translations;
}
