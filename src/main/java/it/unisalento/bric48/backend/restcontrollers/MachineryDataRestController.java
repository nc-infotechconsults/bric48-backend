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
import org.springframework.web.bind.annotation.RequestParam;
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


    //Get machineryData by type
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/find/{type}", method= RequestMethod.GET)
    public List<MachineryDataDTO> getMachineryDataByType(@PathVariable("type") String type) {

        List<MachineryDataDTO> data = new ArrayList<>();

        for(MachineryData machineryData : machineryDataRepository.findByType(type)) {

            MachineryDataDTO machineryDataDTO = new MachineryDataDTO();

            machineryDataDTO.setId(machineryData.getId());
            machineryDataDTO.setType(machineryData.getType());
            machineryDataDTO.setValue(machineryData.getValue());
            machineryDataDTO.setDescription(machineryData.getDescription());
            machineryDataDTO.setTimestamp(machineryData.getTimestamp());
            machineryDataDTO.setMserial(machineryData.getMserial());

            data.add(machineryDataDTO);
        }

        return data;

    }


    //Get machineryData by type and mserial
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public List<MachineryDataDTO> getMachineryDataByTypeAndMserial(@RequestParam("type") String type, @RequestParam("mserial") String mserial){

        List<MachineryDataDTO> data = new ArrayList<>();

        for(MachineryData machineryData : machineryDataRepository.findByTypeAndMserial(type, mserial)) {

            MachineryDataDTO machineryDataDTO = new MachineryDataDTO();

            machineryDataDTO.setId(machineryData.getId());
            machineryDataDTO.setType(machineryData.getType());
            machineryDataDTO.setValue(machineryData.getValue());
            machineryDataDTO.setDescription(machineryData.getDescription());
            machineryDataDTO.setTimestamp(machineryData.getTimestamp());
            machineryDataDTO.setMserial(machineryData.getMserial());

            data.add(machineryDataDTO);
        }

        return data;

    }
    
}
