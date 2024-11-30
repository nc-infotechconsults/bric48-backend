package it.infotechconsults.bric48.backend.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultMessageResponseDTO extends AuditResponseDTO {
    private String title;
    private String message;
}
