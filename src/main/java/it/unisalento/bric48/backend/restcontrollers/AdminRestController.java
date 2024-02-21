package it.unisalento.bric48.backend.restcontrollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.Admin;
import it.unisalento.bric48.backend.dto.AdminDTO;
import it.unisalento.bric48.backend.dto.AuthenticationResponseDTO;
import it.unisalento.bric48.backend.dto.LoginDTO;
import it.unisalento.bric48.backend.repositories.AdminRepository;
import it.unisalento.bric48.backend.security.JwtUtilities;

import static it.unisalento.bric48.backend.configuration.SecurityConfig.passwordEncoder;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilities jwtUtilities;

    // Test API
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    // Get JWT Token
    @RequestMapping(value="/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        Admin admin = adminRepository.findByEmail(authentication.getName());

        if(admin == null) {
            throw new UsernameNotFoundException(loginDTO.getEmail());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = jwtUtilities.generateToken(admin.getEmail());

        return ResponseEntity.ok(new AuthenticationResponseDTO(jwt));

    }

    // Add a new admin
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AdminDTO addAdmin(@RequestBody AdminDTO adminDTO) {

        Admin newAdmin = new Admin();
        newAdmin.setName(adminDTO.getName());
        newAdmin.setSurname(adminDTO.getSurname());
        newAdmin.setEmail(adminDTO.getEmail());
        newAdmin.setPhoneNumber(adminDTO.getPhoneNumber());
        newAdmin.setPassword(passwordEncoder().encode(adminDTO.getPassword()));

        newAdmin = adminRepository.save(newAdmin);

        adminDTO.setId(newAdmin.getId());
        adminDTO.setPassword(null);

        return adminDTO;
    }

}
