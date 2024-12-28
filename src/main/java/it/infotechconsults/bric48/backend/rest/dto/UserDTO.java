package it.infotechconsults.bric48.backend.rest.dto;

import java.util.HashSet;
import java.util.Set;

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
    private String language;
    private String headphoneId = "";
    private Set<String> machineriesId = new HashSet<>();
}
