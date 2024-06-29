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

import it.unisalento.bric48.backend.domain.Beacon;
import it.unisalento.bric48.backend.dto.BeaconDTO;
import it.unisalento.bric48.backend.repositories.BeaconRepository;

@RestController
@CrossOrigin
@RequestMapping("/beacon")
public class BeaconRestController {

    @Autowired
    BeaconRepository beaconRepository;

    // Add a new beacon
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BeaconDTO addBeacon(@RequestBody BeaconDTO beaconDTO) {

        Beacon newBeacon = new Beacon();
        newBeacon.setMac(beaconDTO.getMac());
        newBeacon.setMserial(beaconDTO.getMserial());
        newBeacon.setThreshold(beaconDTO.getThreshold());

        newBeacon = beaconRepository.save(newBeacon);

        beaconDTO.setId(newBeacon.getId());

        return beaconDTO;
    }

    //Get all beacons
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/getAll", method= RequestMethod.GET)
    public List<BeaconDTO> getAllBeacons() {

        List<BeaconDTO> beacons = new ArrayList<>();

        for(Beacon beacon : beaconRepository.findAll()) {

            BeaconDTO beaconDTO = new BeaconDTO();

            beaconDTO.setId(beacon.getId());
            beaconDTO.setMac(beacon.getMac());
            beaconDTO.setMserial(beacon.getMserial());
            beaconDTO.setThreshold(beacon.getThreshold());

            beacons.add(beaconDTO);
        }

        return beacons;
    }

    //Get beacons from-to
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/getBeaconsFromTo", method= RequestMethod.GET)
    public List<BeaconDTO> getBeaconsFromTo(@RequestParam("from") String from, @RequestParam("to") String to) {

        int c = 1;
        int i = Integer.parseInt(from);
        int j = Integer.parseInt(to);

        List<BeaconDTO> beacons = new ArrayList<>();

        for(Beacon beacon : beaconRepository.findAll()) {

            if(c >= i && c <= j){

                BeaconDTO beaconDTO = new BeaconDTO();

                beaconDTO.setId(beacon.getId());
                beaconDTO.setMac(beacon.getMac());
                beaconDTO.setMserial(beacon.getMserial());
                beaconDTO.setThreshold(beacon.getThreshold());

                beacons.add(beaconDTO);
            }

            c++;
        }

        return beacons;
    }

    //Get beacons by mserial
    @RequestMapping(value="/find/{mserial}", method= RequestMethod.GET)
    public List<BeaconDTO> getBeaconByMserial(@PathVariable("mserial") String mserial) {

        List<BeaconDTO> beacons = new ArrayList<>();

        for(Beacon beacon : beaconRepository.findByMserial(mserial)) {
            BeaconDTO beaconDTO = new BeaconDTO();
            beaconDTO.setId(beacon.getId());
            beaconDTO.setMac(beacon.getMac());
            beaconDTO.setMserial(beacon.getMserial());
            beaconDTO.setThreshold(beacon.getThreshold());

            beacons.add(beaconDTO);
        }

        return beacons;
    }

    //Get beacon by mac
    @RequestMapping(value="/findByMac/{mac}", method= RequestMethod.GET)
    public BeaconDTO getBeaconByMac(@PathVariable("mac") String mac) {

        Optional<Beacon> beaconOpt = beaconRepository.findByMac(mac);

        if (beaconOpt.isPresent()){
            Beacon beacon = beaconOpt.get();

            BeaconDTO beaconDTO = new BeaconDTO();
            beaconDTO.setId(beacon.getId());
            beaconDTO.setMac(beacon.getMac());
            beaconDTO.setMserial(beacon.getMserial());
            beaconDTO.setThreshold(beacon.getThreshold());

            return beaconDTO;
        }else{
            return null;
        }
        
    }

    // Delete beacon by mac
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete/{mac}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBeaconByMac(@PathVariable("mac") String mac) {
  
        beaconRepository.deleteByMac(mac);

        // Verifica se l'entità è stata eliminata con successo
        Optional<Beacon> deletedEntity = beaconRepository.findByMac(mac);
        if (!deletedEntity.isEmpty()) {
            return ResponseEntity.badRequest().body("ID not found");
        }
        return ResponseEntity.ok().build();
    }


    // Update beacon
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/updateBeacon", method = RequestMethod.PUT)
    public ResponseEntity<String> updateBeacon(@RequestBody Beacon editedBeacon) {

        Optional<Beacon> existingBeaconOpt = beaconRepository.findById(editedBeacon.getId());

        if (existingBeaconOpt.isPresent()) {
            beaconRepository.save(editedBeacon);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("ID not found");
        }
    }

    
    // Update beacon by mserial
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/updateMserial", method = RequestMethod.PUT)
    public ResponseEntity<String> updateMserial(@RequestParam("oldMserial") String oldMserial, @RequestParam("newMserial") String newMserial) {

        List<Beacon> beacons = beaconRepository.findByMserial(oldMserial);

        if (beacons != null && !beacons.isEmpty()) {
            for (Beacon beacon : beacons) {
                beacon.setMserial(newMserial);
                beaconRepository.save(beacon);
            }
        }

        return ResponseEntity.ok().build();
    }

}

