package it.infotechconsults.bric48.backend.rest.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO extends AuditResponseDTO {
    private String name;
    private String surname;
    private String email;
    private String language;
    private String regNumber;
    private String phoneNumber;
    private RoleResponseDTO role;
    private HeadphoneResponseDTO headphone;
    private Set<MachineryResponseDTO> machineries;
}
