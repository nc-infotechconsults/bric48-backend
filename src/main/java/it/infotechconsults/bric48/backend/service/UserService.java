package it.infotechconsults.bric48.backend.service;

import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.mapper.UserMapper;
import it.infotechconsults.bric48.backend.repository.UserRepository;
import it.infotechconsults.bric48.backend.rest.dto.UserDTO;

@Service
public class UserService extends AuditService<UserDTO, User, User, String> {

    public UserService(UserRepository repository, UserMapper mapper){
        super(repository, mapper);
    }
    
}
