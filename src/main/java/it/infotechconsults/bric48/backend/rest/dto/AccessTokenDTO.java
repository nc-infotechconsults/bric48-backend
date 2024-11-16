package it.infotechconsults.bric48.backend.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AccessTokenDTO {
    private String token;
    private Integer expireIn;
}
