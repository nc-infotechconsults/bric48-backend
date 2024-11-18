package it.infotechconsults.bric48.backend.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.domain.Beacon;
import it.infotechconsults.bric48.backend.mapper.BeaconMapper;
import it.infotechconsults.bric48.backend.rest.dto.BeaconDTO;
import it.infotechconsults.bric48.backend.service.BeaconService;

@RestController
@RequestMapping("/beacons")
public class BeaconController extends BaseController<BeaconDTO, Beacon, Beacon, String>{
    
    public BeaconController(BeaconService service, BeaconMapper mapper){
        super(service, mapper);
    }

}
