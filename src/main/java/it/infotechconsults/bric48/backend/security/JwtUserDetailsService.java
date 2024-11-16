package it.infotechconsults.bric48.backend.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.enums.LogicOperator;
import it.infotechconsults.bric48.backend.enums.QueryOperation;
import it.infotechconsults.bric48.backend.rest.dto.FilterCriteriaDTO;
import it.infotechconsults.bric48.backend.rest.dto.FiltersDTO;
import it.infotechconsults.bric48.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            Page<User> userDetail = userService.search(
                    FiltersDTO.builder()
                            .criterias(
                                    Collections.singletonList(
                                            FilterCriteriaDTO.builder()
                                                    .field("email")
                                                    .operation(QueryOperation.EQUAL_IGNORECASE)
                                                    .value(username)
                                                    .build()))
                            .operator(LogicOperator.AND)
                            .build(),
                    Pageable.unpaged());

            if (userDetail.getSize() > 1)
                throw new UsernameNotFoundException("Multiple user found with the username: " + username);

            // Converting UserInfo to UserDetails
            return userDetail.get().findFirst().map(JwtUserDetailsInfo::new)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
                    
        } catch (Exception e) {
            log.error("Error happen: {}", e.getMessage());
            log.debug("Detail stacktrace: ", e);
            throw new UsernameNotFoundException("Error happen during search for username: " + username);
        }

    }

}
