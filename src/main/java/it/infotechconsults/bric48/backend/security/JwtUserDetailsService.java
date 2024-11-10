package it.infotechconsults.bric48.backend.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.service.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = userService.findByEmail(username);

        // Converting UserInfo to UserDetails
        return userDetail.map(JwtUserDetailsInfo::new).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

}
