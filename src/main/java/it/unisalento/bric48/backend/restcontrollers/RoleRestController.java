package it.unisalento.bric48.backend.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.Role;
import it.unisalento.bric48.backend.dto.RoleDTO;
import it.unisalento.bric48.backend.repositories.RoleRepository;

@RestController
@CrossOrigin
@RequestMapping("/role")
public class RoleRestController {

    @Autowired
    RoleRepository roleRepository;

    // Add a new role
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RoleDTO addRole(@RequestBody RoleDTO roleDTO) {

        Role newRole = new Role();
        newRole.setName(roleDTO.getName());

        newRole = roleRepository.save(newRole);

        roleDTO.setId(newRole.getId());

        return roleDTO;
    }

}
