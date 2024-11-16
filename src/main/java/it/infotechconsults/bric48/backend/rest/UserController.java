package it.infotechconsults.bric48.backend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.mapper.UserMapper;
import it.infotechconsults.bric48.backend.rest.dto.UserDTO;
import it.infotechconsults.bric48.backend.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController<UserDTO, User, User, String>{
    
    public UserController(UserService service, UserMapper mapper){
        super(service, mapper);
    }

}
