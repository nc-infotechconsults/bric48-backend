package it.infotechconsults.bric48.backend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.domain.Machinery;
import it.infotechconsults.bric48.backend.mapper.MachineryMapper;
import it.infotechconsults.bric48.backend.rest.dto.MachineryDTO;
import it.infotechconsults.bric48.backend.rest.dto.MachineryResponseDTO;
import it.infotechconsults.bric48.backend.service.MachineryService;

@RestController
@RequestMapping("/machineries")
public class MachineryController extends BaseController<MachineryDTO, MachineryResponseDTO, Machinery, String>{
    
    public MachineryController(MachineryService service, MachineryMapper mapper){
        super(service, mapper);
    }

}
