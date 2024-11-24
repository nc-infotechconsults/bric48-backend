package it.infotechconsults.bric48.backend.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private String name;
    private String surname;
    private String email;
    private String regNumber;
    private String phoneNumber;
    private String password;
    private RoleDTO role;
    private String headphoneId;
}
