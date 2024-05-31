package it.unisalento.bric48.backend.restcontrollers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.Headphones;
import it.unisalento.bric48.backend.dto.HeadphonesDTO;
import it.unisalento.bric48.backend.repositories.HeadphonesRepository;
import it.unisalento.bric48.backend.repositories.NearbyHeadphonesRepository;

@RestController
@CrossOrigin
@RequestMapping("/headphones")
public class HeadphonesRestController {

    @Autowired
    HeadphonesRepository headphonesRepository;

    @Autowired
    NearbyHeadphonesRepository nearbyHeadphonesRepository;

    // Add a new headphones
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HeadphonesDTO addHeadphones(@RequestBody HeadphonesDTO headphonesDTO) {

        Headphones newHeadphones = new Headphones();
        newHeadphones.setSerial(headphonesDTO.getSerial());
        newHeadphones.setIsAssociated(headphonesDTO.getIsAssociated());

        newHeadphones = headphonesRepository.save(newHeadphones);

        headphonesDTO.setId(newHeadphones.getId());

        return headphonesDTO;
    }

    //Get all headphones
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/getAll", method= RequestMethod.GET)
    public List<HeadphonesDTO> getAllHeadphones() {

        List<HeadphonesDTO> headphones = new ArrayList<>();

        for(Headphones h : headphonesRepository.findAll()) {

            HeadphonesDTO headphonesDTO = new HeadphonesDTO();

            headphonesDTO.setId(h.getId());
            headphonesDTO.setSerial(h.getSerial());
            headphonesDTO.setIsAssociated(h.getIsAssociated());

            headphones.add(headphonesDTO);
        }

        return headphones;
    }


    //Get headphones from-to
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/getHeadphonesFromTo", method= RequestMethod.GET)
    public List<HeadphonesDTO> getHeadphonesFromTo(@RequestParam("from") String from, @RequestParam("to") String to) {

        int c = 1;
        int i = Integer.parseInt(from);
        int j = Integer.parseInt(to);

        List<HeadphonesDTO> headphones = new ArrayList<>();

        for(Headphones h : headphonesRepository.findAll()) {

            if(c >= i && c <= j){

                HeadphonesDTO headphonesDTO = new HeadphonesDTO();

                headphonesDTO.setId(h.getId());
                headphonesDTO.setSerial(h.getSerial());
                headphonesDTO.setIsAssociated(h.getIsAssociated());

                headphones.add(headphonesDTO);
            }

            c++;
        }

        return headphones;
    }


    //Get headphones by isAssociated
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/find/{isAssociated}", method= RequestMethod.GET)
    public List<HeadphonesDTO> getHeadphonesByIsAssociated(@PathVariable("isAssociated") String isAssociated) {

        List<HeadphonesDTO> headphones = new ArrayList<>();

        for(Headphones h : headphonesRepository.findByIsAssociated(isAssociated)) {
            HeadphonesDTO headphonesDTO = new HeadphonesDTO();
            headphonesDTO.setId(h.getId());
            headphonesDTO.setSerial(h.getSerial());
            headphonesDTO.setIsAssociated(h.getIsAssociated());

            headphones.add(headphonesDTO);
        }

        return headphones;
    }

    // Delete headphones by serial
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete/{serial}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteHeadphonesBySerial(@PathVariable("serial") String serial) {
  
        headphonesRepository.deleteBySerial(serial);

        // Verifica se l'entità è stata eliminata con successo
        Optional<Headphones> deletedEntity = headphonesRepository.findBySerial(serial);
        if (!deletedEntity.isEmpty()) {
            return ResponseEntity.badRequest().body("ID not found");
        }

        nearbyHeadphonesRepository.deleteBySerial(serial);

        return ResponseEntity.ok().build();
    }

    // Update association by serial
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/updateAssociation", method = RequestMethod.PUT)
    public ResponseEntity<String> updateIsAssociated(@RequestParam("serial") String serial, @RequestParam("isAssociated") String isAssociated) {

        Optional<Headphones> existingHeadphonesOpt = headphonesRepository.findBySerial(serial);

        if (existingHeadphonesOpt.isPresent()) {
            Headphones existingHeadphones = existingHeadphonesOpt.get();
            existingHeadphones.setIsAssociated(isAssociated);
            existingHeadphones = headphonesRepository.save(existingHeadphones);
            
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("ID not found");
        }
    }


    //Get headphones by serial
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/find", method= RequestMethod.GET)
    public HeadphonesDTO getHeadphonesBySerial(@RequestParam("serial") String serial) {

        HeadphonesDTO headphonesDTO = new HeadphonesDTO();

        Optional<Headphones> headphonesOpt = headphonesRepository.findBySerial(serial);

        if(headphonesOpt.isPresent()){

            Headphones headphones = headphonesOpt.get();

            headphonesDTO.setId(headphones.getId());
            headphonesDTO.setSerial(headphones.getSerial());
            headphonesDTO.setIsAssociated(headphones.getIsAssociated());
        }

        return headphonesDTO;
    }


}
