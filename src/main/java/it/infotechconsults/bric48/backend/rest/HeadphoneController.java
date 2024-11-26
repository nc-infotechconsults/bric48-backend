package it.infotechconsults.bric48.backend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.domain.Headphone;
import it.infotechconsults.bric48.backend.mapper.HeadphoneMapper;
import it.infotechconsults.bric48.backend.rest.dto.HeadphoneDTO;
import it.infotechconsults.bric48.backend.rest.dto.HeadphoneResponseDTO;
import it.infotechconsults.bric48.backend.service.HeadphoneService;

@RestController
@RequestMapping("/headphones")
public class HeadphoneController extends BaseController<HeadphoneDTO, HeadphoneResponseDTO, Headphone, String>{
    
    public HeadphoneController(HeadphoneService service, HeadphoneMapper mapper){
        super(service, mapper);
    }

}
