package it.infotechconsults.bric48.backend.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeaconDTO {
    private String name;
    private String serial;
    private Float threshold;
}
