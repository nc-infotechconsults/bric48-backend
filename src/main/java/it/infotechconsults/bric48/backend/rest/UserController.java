package it.infotechconsults.bric48.backend.rest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.mapper.UserMapper;
import it.infotechconsults.bric48.backend.rest.dto.UserDTO;
import it.infotechconsults.bric48.backend.rest.dto.UserResponseDTO;
import it.infotechconsults.bric48.backend.service.MqttService;
import it.infotechconsults.bric48.backend.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController<UserDTO, UserResponseDTO, User, String>{

    @Autowired
    private MqttService mqttService;

    public UserController(UserService service, UserMapper mapper){
        super(service, mapper);
    }

    @GetMapping("/userNearMachinery/{machineryId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Set<UserResponseDTO> getUserNearMachinery(@PathVariable("machineryId") String machineryId) throws Exception {
        return mqttService.getUserNearMachineries().get(machineryId);
    }

}
