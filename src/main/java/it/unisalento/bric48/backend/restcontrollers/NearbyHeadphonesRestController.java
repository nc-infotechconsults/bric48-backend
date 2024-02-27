package it.unisalento.bric48.backend.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.NearbyHeadphones;
import it.unisalento.bric48.backend.dto.NearbyHeadphonesDTO;
import it.unisalento.bric48.backend.repositories.NearbyHeadphonesRepository;

@RestController
@CrossOrigin
@RequestMapping("/nearbyHeadphones")
public class NearbyHeadphonesRestController {

    @Autowired
    NearbyHeadphonesRepository nearbyHeadphonesRepository;

    // Add a new NearbyHeadphones document
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public NearbyHeadphonesDTO addNearbyHeadphones(@RequestBody NearbyHeadphonesDTO nearbyHeadphonesDTO) {

        NearbyHeadphones newNearbyHeadphones = new NearbyHeadphones();
        newNearbyHeadphones.setSerial(nearbyHeadphonesDTO.getSerial());
        newNearbyHeadphones.setMserial(nearbyHeadphonesDTO.getMserial());

        newNearbyHeadphones = nearbyHeadphonesRepository.save(newNearbyHeadphones);

        nearbyHeadphonesDTO.setId(newNearbyHeadphones.getId());

        return nearbyHeadphonesDTO;
    }

    // Get all document
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/getAll", method= RequestMethod.GET)
    public List<NearbyHeadphonesDTO> getAllNearbyHeadphones() {

        List<NearbyHeadphonesDTO> nearbyHeadphones = new ArrayList<>();

        for(NearbyHeadphones nearbyH : nearbyHeadphonesRepository.findAll()) {

            NearbyHeadphonesDTO nearbyHeadphonesDTO = new NearbyHeadphonesDTO();

            nearbyHeadphonesDTO.setId(nearbyH.getId());
            nearbyHeadphonesDTO.setSerial(nearbyH.getSerial());
            nearbyHeadphonesDTO.setMserial(nearbyH.getMserial());

            nearbyHeadphones.add(nearbyHeadphonesDTO);
        }

        return nearbyHeadphones;
    }

    // Get NearbyHeadphones by mserial
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/find/{mserial}", method= RequestMethod.GET)
    public List<NearbyHeadphonesDTO> getNearbyHeadphonesByMserial(@PathVariable("mserial") String mserial) {

        List<NearbyHeadphonesDTO> nearbyHeadphones = new ArrayList<>();

        for(NearbyHeadphones nearbyH : nearbyHeadphonesRepository.findByMserial(mserial)) {
            NearbyHeadphonesDTO nearbyHeadphonesDTO = new NearbyHeadphonesDTO();
            nearbyHeadphonesDTO.setId(nearbyH.getId());
            nearbyHeadphonesDTO.setSerial(nearbyH.getSerial());
            nearbyHeadphonesDTO.setMserial(nearbyH.getMserial());

            nearbyHeadphones.add(nearbyHeadphonesDTO);
        }

        return nearbyHeadphones;
    }

    // Delete nearbyHeadphones
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteHeadphones(@RequestBody NearbyHeadphonesDTO deleteData) {

        String serial = deleteData.getSerial();
        String mserial = deleteData.getMserial();

        String id = "";

        for(NearbyHeadphones nearbyHeadphones : nearbyHeadphonesRepository.findByMserial(mserial)) {
            if (nearbyHeadphones.getSerial().equals(serial)) {
                id = nearbyHeadphones.getId();
                nearbyHeadphonesRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }
        }

        return ResponseEntity.badRequest().body("ID not found");
        
    }







}
