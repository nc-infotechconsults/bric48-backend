package it.infotechconsults.bric48.backend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.domain.MachineryNotification;
import it.infotechconsults.bric48.backend.mapper.MachineryNotificationMapper;
import it.infotechconsults.bric48.backend.rest.dto.MachineryNotificationDTO;
import it.infotechconsults.bric48.backend.rest.dto.MachineryNotificationResponseDTO;
import it.infotechconsults.bric48.backend.service.MachineryNotificationService;

@RestController
@RequestMapping("/machineryNotifications")
public class MachineryNotificationController extends BaseController<MachineryNotificationDTO, MachineryNotificationResponseDTO, MachineryNotification, String>{
    
    public MachineryNotificationController(MachineryNotificationService service, MachineryNotificationMapper mapper){
        super(service, mapper);
    }

}
