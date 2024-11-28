package it.infotechconsults.bric48.backend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.domain.Role;
import it.infotechconsults.bric48.backend.mapper.RoleMapper;
import it.infotechconsults.bric48.backend.rest.dto.RoleDTO;
import it.infotechconsults.bric48.backend.rest.dto.RoleResponseDTO;
import it.infotechconsults.bric48.backend.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController<RoleDTO, RoleResponseDTO, Role, String>{
    
    public RoleController(RoleService service, RoleMapper mapper){
        super(service, mapper);
    }

}
