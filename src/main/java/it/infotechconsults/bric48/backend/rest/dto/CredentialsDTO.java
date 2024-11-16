package it.infotechconsults.bric48.backend.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CredentialsDTO {
    private String username;
    private String password;
}
