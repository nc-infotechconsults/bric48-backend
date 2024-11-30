package it.infotechconsults.bric48.backend.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditResponseDTO extends BaseResponseDTO {
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;
}
