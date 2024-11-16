package it.infotechconsults.bric48.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.rest.dto.AccessTokenDTO;
import it.infotechconsults.bric48.backend.rest.dto.CredentialsDTO;
import it.infotechconsults.bric48.backend.security.JwtService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/token")
    public AccessTokenDTO authenticateAndGetToken(@RequestBody CredentialsDTO authRequest) throws Exception{
        log.debug("auth::token - {}", authRequest.toString());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return AccessTokenDTO.builder()
            .token(jwtService.generateToken(authRequest.getUsername()))
            .expireIn(jwtService.EXPIRATION)
            .build();
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

}