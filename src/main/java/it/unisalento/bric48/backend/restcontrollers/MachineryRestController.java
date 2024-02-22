package it.unisalento.bric48.backend.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.Machinery;
import it.unisalento.bric48.backend.dto.MachineryDTO;
import it.unisalento.bric48.backend.repositories.MachineryRepository;

@RestController
@RequestMapping("/machinery")
public class MachineryRestController {

    @Autowired
    MachineryRepository machineryRepository;

    // Add a new machinery
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MachineryDTO addMachinery(@RequestBody MachineryDTO machineryDTO) {

        Machinery newMachinery = new Machinery();
        newMachinery.setMserial(machineryDTO.getMserial());
        newMachinery.setName(machineryDTO.getName());
        newMachinery.setIdRoom(machineryDTO.getIdRoom());
        
        newMachinery = machineryRepository.save(newMachinery);

        machineryDTO.setId(newMachinery.getId());

        return machineryDTO;
    }

}
