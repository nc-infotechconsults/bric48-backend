package it.infotechconsults.bric48.backend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.domain.NotificationCode;
import it.infotechconsults.bric48.backend.mapper.NotificationCodeMapper;
import it.infotechconsults.bric48.backend.rest.dto.NotificationCodeDTO;
import it.infotechconsults.bric48.backend.rest.dto.NotificationCodeResponseDTO;
import it.infotechconsults.bric48.backend.service.NotificationCodeService;

@RestController
@RequestMapping("/notificationCodes")
public class NotificationCodeController extends BaseController<NotificationCodeDTO, NotificationCodeResponseDTO, NotificationCode, String>{
    
    public NotificationCodeController(NotificationCodeService service, NotificationCodeMapper mapper){
        super(service, mapper);
    }

}
