package it.unisalento.bric48.backend.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.Machinery;
import it.unisalento.bric48.backend.dto.MachineryDTO;
import it.unisalento.bric48.backend.repositories.MachineryRepository;

@RestController
@CrossOrigin
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
        newMachinery.setTopic(machineryDTO.getTopic());
        newMachinery.setIdRoom(machineryDTO.getIdRoom());
        
        newMachinery = machineryRepository.save(newMachinery);

        machineryDTO.setId(newMachinery.getId());

        return machineryDTO;
    }

    //Get all machineries
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/getAll", method= RequestMethod.GET)
    public List<MachineryDTO> getAllMachineries() {

        List<MachineryDTO> machineries = new ArrayList<>();

        for(Machinery machinery : machineryRepository.findAll()) {

            MachineryDTO machineryDTO = new MachineryDTO();

            machineryDTO.setId(machinery.getId());
            machineryDTO.setMserial(machinery.getMserial());
            machineryDTO.setName(machinery.getName());
            machineryDTO.setTopic(machinery.getTopic());
            machineryDTO.setIdRoom(machinery.getIdRoom());

            machineries.add(machineryDTO);
        }

        return machineries;
    }


    //Get machineries by room
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/find/{idRoom}", method= RequestMethod.GET)
    public List<MachineryDTO> getMachineryByIdRoom(@PathVariable("idRoom") String idRoom) {

        List<MachineryDTO> machineries = new ArrayList<>();

        for(Machinery machinery : machineryRepository.findByIdRoom(idRoom)) {
            MachineryDTO machineryDTO = new MachineryDTO();
            machineryDTO.setId(machinery.getId());
            machineryDTO.setMserial(machinery.getMserial());
            machineryDTO.setName(machinery.getName());
            machineryDTO.setTopic(machinery.getTopic());
            machineryDTO.setIdRoom(machinery.getIdRoom());

            machineries.add(machineryDTO);
        }

        return machineries;
    }

}
