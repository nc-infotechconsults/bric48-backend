package it.infotechconsults.bric48.backend.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaResponseDTO extends BaseResponseDTO {
    private String name;
    private String description;
    private StructureResponseDTO structure;
}