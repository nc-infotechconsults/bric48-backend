package it.infotechconsults.bric48.backend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.domain.Message;
import it.infotechconsults.bric48.backend.mapper.MessageMapper;
import it.infotechconsults.bric48.backend.rest.dto.MessageDTO;
import it.infotechconsults.bric48.backend.rest.dto.MessageResponseDTO;
import it.infotechconsults.bric48.backend.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController extends BaseController<MessageDTO, MessageResponseDTO, Message, String>{
    
    public MessageController(MessageService service, MessageMapper mapper){
        super(service, mapper);
    }

}
