package it.infotechconsults.bric48.backend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.domain.Area;
import it.infotechconsults.bric48.backend.mapper.AreaMapper;
import it.infotechconsults.bric48.backend.rest.dto.AreaDTO;
import it.infotechconsults.bric48.backend.service.AreaService;

@RestController
@RequestMapping("/areas")
public class AreaController extends BaseController<AreaDTO, Area, Area, String>{
    
    public AreaController(AreaService service, AreaMapper mapper){
        super(service, mapper);
    }

}
