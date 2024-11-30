package it.infotechconsults.bric48.backend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.domain.DefaultMessage;
import it.infotechconsults.bric48.backend.mapper.DefaultMessageMapper;
import it.infotechconsults.bric48.backend.rest.dto.DefaultMessageDTO;
import it.infotechconsults.bric48.backend.rest.dto.DefaultMessageResponseDTO;
import it.infotechconsults.bric48.backend.service.DefaultMessageService;

@RestController
@RequestMapping("/defaultMessages")
public class DefaultMessageController extends BaseController<DefaultMessageDTO, DefaultMessageResponseDTO, DefaultMessage, String>{
    
    public DefaultMessageController(DefaultMessageService service, DefaultMessageMapper mapper){
        super(service, mapper);
    }

}
