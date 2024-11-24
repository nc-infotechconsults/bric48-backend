package it.infotechconsults.bric48.backend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.domain.Structure;
import it.infotechconsults.bric48.backend.mapper.StructureMapper;
import it.infotechconsults.bric48.backend.rest.dto.StructureDTO;
import it.infotechconsults.bric48.backend.rest.dto.StructureResponseDTO;
import it.infotechconsults.bric48.backend.service.StructureService;

@RestController
@RequestMapping("/structures")
public class StructureController extends BaseController<StructureDTO, StructureResponseDTO, Structure, String>{
    
    public StructureController(StructureService service, StructureMapper mapper){
        super(service, mapper);
    }

}
