package it.unisalento.bric48.backend.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.MachineryData;
import it.unisalento.bric48.backend.dto.MachineryDataDTO;
import it.unisalento.bric48.backend.repositories.MachineryDataRepository;


@RestController
@CrossOrigin
@RequestMapping("/data")
public class MachineryDataRestController {

    @Autowired
    MachineryDataRepository machineryDataRepository;

    // Add a new machinery data
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MachineryDataDTO addMachineryData(@RequestBody MachineryDataDTO machineryDataDTO) {

        MachineryData newMachineryData = new MachineryData();
        newMachineryData.setMserial(machineryDataDTO.getMserial());
        newMachineryData.setType(machineryDataDTO.getType());
        newMachineryData.setValue(machineryDataDTO.getValue());
        newMachineryData.setDescription(machineryDataDTO.getDescription());
        newMachineryData.setTimestamp(machineryDataDTO.getTimestamp());
        
        newMachineryData = machineryDataRepository.save(newMachineryData);

        machineryDataDTO.setId(newMachineryData.getId());

        return machineryDataDTO;
    }
    
}
