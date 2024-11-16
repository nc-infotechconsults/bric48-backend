package it.infotechconsults.bric48.backend.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String surname;
    private String email;
    private String regNumber;
    private String phoneNumber;
    private String password;
    private String roleId;
    private String headphoneId;
}
