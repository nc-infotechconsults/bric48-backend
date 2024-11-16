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

import it.infotechconsults.bric48.backend.rest.request.AuthRequest;
import it.infotechconsults.bric48.backend.rest.response.AuthResponse;
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
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws Exception{
        log.debug("auth::token - {}", authRequest.toString());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return AuthResponse.builder().token(jwtService.generateToken(authRequest.getUsername())).build();
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

}