package it.infotechconsults.bric48.backend.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @PatchMapping("/{id}/solve")
    @ResponseStatus(code = HttpStatus.OK)
    public void resolve(@PathVariable("id") String id) throws Exception {
        ((MachineryNotificationService)service).resolve(id);
    }

}
