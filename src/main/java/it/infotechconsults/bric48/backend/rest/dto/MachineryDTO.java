package it.infotechconsults.bric48.backend.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MachineryDTO {
    private String name;
    private String serial;
    private String description;
    private String areaId;
}
