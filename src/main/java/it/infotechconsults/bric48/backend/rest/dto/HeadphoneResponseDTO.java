package it.infotechconsults.bric48.backend.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeadphoneResponseDTO extends AuditResponseDTO {
    private String name;
    private String serial;
}
