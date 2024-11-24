package it.infotechconsults.bric48.backend.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeadphoneResponseDTO extends BaseResponseDTO {
    private String name;
    private String serial;
    private String userId;
}
