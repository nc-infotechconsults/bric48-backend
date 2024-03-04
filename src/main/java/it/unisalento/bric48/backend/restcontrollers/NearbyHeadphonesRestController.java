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
        newNearbyHeadphones.setIdRoom(nearbyHeadphonesDTO.getIdRoom());
        newNearbyHeadphones.setIdBranch(nearbyHeadphonesDTO.getIdBranch());

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
            nearbyHeadphonesDTO.setIdRoom(nearbyH.getIdRoom());
            nearbyHeadphonesDTO.setIdBranch(nearbyH.getIdBranch());

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
            nearbyHeadphonesDTO.setIdRoom(nearbyH.getIdRoom());
            nearbyHeadphonesDTO.setIdBranch(nearbyH.getIdBranch());

            nearbyHeadphones.add(nearbyHeadphonesDTO);
        }

        return nearbyHeadphones;
    }

    
    // Get NearbyHeadphones by idRoom
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/find/room/{idRoom}", method= RequestMethod.GET)
    public List<NearbyHeadphonesDTO> getNearbyHeadphonesByIdRoom(@PathVariable("idRoom") String idRoom) {

        List<NearbyHeadphonesDTO> nearbyHeadphones = new ArrayList<>();

        for(NearbyHeadphones nearbyH : nearbyHeadphonesRepository.findByIdRoom(idRoom)) {
            NearbyHeadphonesDTO nearbyHeadphonesDTO = new NearbyHeadphonesDTO();
            nearbyHeadphonesDTO.setId(nearbyH.getId());
            nearbyHeadphonesDTO.setSerial(nearbyH.getSerial());
            nearbyHeadphonesDTO.setMserial(nearbyH.getMserial());
            nearbyHeadphonesDTO.setIdRoom(nearbyH.getIdRoom());
            nearbyHeadphonesDTO.setIdBranch(nearbyH.getIdBranch());

            nearbyHeadphones.add(nearbyHeadphonesDTO);
        }

        return nearbyHeadphones;
    }

    // Get NearbyHeadphones by idBranch
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/find/branch/{idBranch}", method= RequestMethod.GET)
    public List<NearbyHeadphonesDTO> getNearbyHeadphonesByIdBranch(@PathVariable("idBranch") String idBranch) {

        List<NearbyHeadphonesDTO> nearbyHeadphones = new ArrayList<>();

        for(NearbyHeadphones nearbyH : nearbyHeadphonesRepository.findByIdBranch(idBranch)) {
            NearbyHeadphonesDTO nearbyHeadphonesDTO = new NearbyHeadphonesDTO();
            nearbyHeadphonesDTO.setId(nearbyH.getId());
            nearbyHeadphonesDTO.setSerial(nearbyH.getSerial());
            nearbyHeadphonesDTO.setMserial(nearbyH.getMserial());
            nearbyHeadphonesDTO.setIdRoom(nearbyH.getIdRoom());
            nearbyHeadphonesDTO.setIdBranch(nearbyH.getIdBranch());

            nearbyHeadphones.add(nearbyHeadphonesDTO);
        }

        return nearbyHeadphones;
    }


    // Delete nearbyHeadphones
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteHeadphones(@RequestParam("serial") String serial, @RequestParam("mserial") String mserial) {

   
        nearbyHeadphonesRepository.deleteBySerialAndMserial(serial, mserial);

        return ResponseEntity.ok().build();

        //return ResponseEntity.badRequest().body("ID not found");
        
    }


    // Delete nearbyHeadphones by serial
    @RequestMapping(value = "/delete/{serial}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteNearbyHeadphonesBySerial(@PathVariable("serial") String serial) {
  
        for(NearbyHeadphones nearbyHeadphones : nearbyHeadphonesRepository.findBySerial(serial)) {
            nearbyHeadphonesRepository.deleteById(nearbyHeadphones.getId());

            // Verifica se l'entità è stata eliminata con successo
            Optional<NearbyHeadphones> deletedEntity = nearbyHeadphonesRepository.findById(nearbyHeadphones.getId());
            if (!deletedEntity.isEmpty()) {
                return ResponseEntity.badRequest().body("ID not found");
            }
        }
        return ResponseEntity.ok().build();
    }


}
