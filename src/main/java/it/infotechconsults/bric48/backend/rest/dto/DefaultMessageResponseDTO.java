package it.infotechconsults.bric48.backend.rest.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultMessageResponseDTO extends AuditResponseDTO {
    private String title;
    private Set<DefaultTranslationMessageResponseDTO> translations;
}
