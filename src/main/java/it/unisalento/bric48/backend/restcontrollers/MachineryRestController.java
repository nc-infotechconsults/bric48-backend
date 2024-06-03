package it.unisalento.bric48.backend.restcontrollers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import it.unisalento.bric48.backend.domain.Machinery;
import it.unisalento.bric48.backend.dto.MachineryDTO;
import it.unisalento.bric48.backend.repositories.BeaconRepository;
import it.unisalento.bric48.backend.repositories.MachineryRepository;
import it.unisalento.bric48.backend.repositories.NearbyHeadphonesRepository;

@RestController
@CrossOrigin
@RequestMapping("/machinery")
public class MachineryRestController {

    @Autowired
    MachineryRepository machineryRepository;

    @Autowired
    BeaconRepository beaconRepository;

    @Autowired
    NearbyHeadphonesRepository nearbyHeadphonesRepository;

    // Add a new machinery
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MachineryDTO addMachinery(@RequestBody MachineryDTO machineryDTO) {

        Machinery newMachinery = new Machinery();
        newMachinery.setMserial(machineryDTO.getMserial());
        newMachinery.setName(machineryDTO.getName());
        newMachinery.setTopic(machineryDTO.getTopic());
        newMachinery.setIdRoom(machineryDTO.getIdRoom());
        newMachinery.setIdBranch(machineryDTO.getIdBranch());
        
        newMachinery = machineryRepository.save(newMachinery);

        machineryDTO.setId(newMachinery.getId());

        return machineryDTO;
    }

    //Get all machineries
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
            machineryDTO.setIdBranch(machinery.getIdBranch());

            machineries.add(machineryDTO);
        }

        return machineries;
    }

    //Get machineries from-to filter
    @RequestMapping(value="/getMachineriesFromTo", method= RequestMethod.GET)
    public List<MachineryDTO> getMachineriesFromTo(@RequestParam("from") String from, 
                                                    @RequestParam("to") String to, 
                                                    @RequestParam(value= "mserial", required = false) String mserial,
                                                    @RequestParam(value= "name", required = false) String name,
                                                    @RequestParam(value= "idBranch", required = false) String idBranch,
                                                    @RequestParam(value= "idRoom", required = false) String idRoom) {

        int i = Integer.parseInt(from);
        int j = Integer.parseInt(to);

        List<MachineryDTO> machineries = new ArrayList<>();

        List<Machinery> allMachineries = machineryRepository.findAll();

        Collections.reverse(allMachineries);

        for(Machinery machinery : allMachineries) {

            MachineryDTO machineryDTO = new MachineryDTO();

            machineryDTO.setId(machinery.getId());
            machineryDTO.setMserial(machinery.getMserial());
            machineryDTO.setName(machinery.getName());
            machineryDTO.setTopic(machinery.getTopic());
            machineryDTO.setIdRoom(machinery.getIdRoom());
            machineryDTO.setIdBranch(machinery.getIdBranch());

            machineries.add(machineryDTO);

            if(mserial != ""){
                machineries = machineries.stream()
                .filter(obj -> obj.getMserial().toLowerCase().contains(mserial.toLowerCase()))
                .collect(Collectors.toList());
            }

            if(name != ""){
                machineries = machineries.stream()
                .filter(obj -> obj.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
            }

            if(idBranch != ""){
                machineries = machineries.stream()
                .filter(obj -> obj.getIdBranch().toLowerCase().contains(idBranch.toLowerCase()))
                .collect(Collectors.toList());
            }

            if(idRoom != ""){
                machineries = machineries.stream()
                .filter(obj -> obj.getIdRoom().toLowerCase().contains(idRoom.toLowerCase()))
                .collect(Collectors.toList());
            }
            
        }

        if(machineries.size() < j){
            j = machineries.size();
        }

        return machineries.subList(i-1, j);
    }


    //Get machineries filtered
    @RequestMapping(value="/getMachineriesFiltered", method= RequestMethod.GET)
    public List<MachineryDTO> getMachineriesFiltered(@RequestParam(value= "mserial", required = false) String mserial,
                                                    @RequestParam(value= "name", required = false) String name,
                                                    @RequestParam(value= "idBranch", required = false) String idBranch,
                                                    @RequestParam(value= "idRoom", required = false) String idRoom) {


        List<MachineryDTO> machineries = new ArrayList<>();

        List<Machinery> allMachineries = machineryRepository.findAll();

        Collections.reverse(allMachineries);

        for(Machinery machinery : allMachineries) {

            MachineryDTO machineryDTO = new MachineryDTO();

            machineryDTO.setId(machinery.getId());
            machineryDTO.setMserial(machinery.getMserial());
            machineryDTO.setName(machinery.getName());
            machineryDTO.setTopic(machinery.getTopic());
            machineryDTO.setIdRoom(machinery.getIdRoom());
            machineryDTO.setIdBranch(machinery.getIdBranch());

            machineries.add(machineryDTO);
            
        }

        if(mserial != ""){
            machineries = machineries.stream()
            .filter(obj -> obj.getMserial().toLowerCase().contains(mserial.toLowerCase()))
            .collect(Collectors.toList());
        }

        if(name != ""){
            machineries = machineries.stream()
            .filter(obj -> obj.getName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
        }

        if(idBranch != ""){
            machineries = machineries.stream()
            .filter(obj -> obj.getIdBranch().toLowerCase().contains(idBranch.toLowerCase()))
            .collect(Collectors.toList());
        }

        if(idRoom != ""){
            machineries = machineries.stream()
            .filter(obj -> obj.getIdRoom().toLowerCase().contains(idRoom.toLowerCase()))
            .collect(Collectors.toList());
        }

        return machineries;
    }


    //Get machineries by room od branch
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/find", method= RequestMethod.GET)
    public List<MachineryDTO> getMachineryByIdRoomOrIdBranch(@RequestParam(value = "idBranch", required = false) String idBranch, @RequestParam(value = "idRoom", required = false) String idRoom) {

        List<MachineryDTO> machineries = new ArrayList<>();

        if(idRoom != null){
            for(Machinery machinery : machineryRepository.findByIdRoom(idRoom)) {
                MachineryDTO machineryDTO = new MachineryDTO();
                machineryDTO.setId(machinery.getId());
                machineryDTO.setMserial(machinery.getMserial());
                machineryDTO.setName(machinery.getName());
                machineryDTO.setTopic(machinery.getTopic());
                machineryDTO.setIdRoom(machinery.getIdRoom());
                machineryDTO.setIdBranch(machinery.getIdBranch());

                machineries.add(machineryDTO);
            }
        }else{
            for(Machinery machinery : machineryRepository.findByIdBranch(idBranch)) {
                MachineryDTO machineryDTO = new MachineryDTO();
                machineryDTO.setId(machinery.getId());
                machineryDTO.setMserial(machinery.getMserial());
                machineryDTO.setName(machinery.getName());
                machineryDTO.setTopic(machinery.getTopic());
                machineryDTO.setIdRoom(machinery.getIdRoom());
                machineryDTO.setIdBranch(machinery.getIdBranch());

                machineries.add(machineryDTO);
            }
        }

            

        return machineries;
    }


    //Get machinery by mserial
    @RequestMapping(value="/find/machinery/{mserial}", method= RequestMethod.GET)
    public MachineryDTO getMachineryByMserial(@PathVariable("mserial") String mserial) {

        Machinery machinery = machineryRepository.findByMserial(mserial);

        MachineryDTO machineryDTO = new MachineryDTO();
        machineryDTO.setId(machinery.getId());
        machineryDTO.setMserial(machinery.getMserial());
        machineryDTO.setName(machinery.getName());
        machineryDTO.setTopic(machinery.getTopic());
        machineryDTO.setIdRoom(machinery.getIdRoom());
        machineryDTO.setIdBranch(machinery.getIdBranch());

        return machineryDTO;
    }


    // Delete machinery by mserial
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete/{mserial}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMachineryByMserial(@PathVariable("mserial") String mserial) {
  
        machineryRepository.deleteByMserial(mserial);

        // Verifica se l'entità è stata eliminata con successo
        Machinery deletedEntity = machineryRepository.findByMserial(mserial);
        if (deletedEntity != null) {
            return ResponseEntity.badRequest().body("ID not found");
        }

        List<Beacon> beacons = beaconRepository.findByMserial(mserial);

        if (beacons != null && !beacons.isEmpty()) {
            for (Beacon beacon : beacons) {
                beacon.setMserial("");
                beaconRepository.save(beacon);
            }
        }

        nearbyHeadphonesRepository.deleteByMserial(mserial);

        return ResponseEntity.ok().build();
    }

    // Update machinery
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/updateMachinery", method = RequestMethod.PUT)
    public ResponseEntity<String> updateMachinery(@RequestBody Machinery editedMachinery) {

        Optional<Machinery> existingMachineryOpt = machineryRepository.findById(editedMachinery.getId());

        if (existingMachineryOpt.isPresent()) {
            machineryRepository.save(editedMachinery);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("ID not found");
        }
    }


    // Update machineries by idBranch
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/updateMachineriesByIdBranch", method = RequestMethod.PUT)
    public ResponseEntity<String> updateMachineryByIdBranch(@RequestParam("oldIdBranch") String oldIdBranch, @RequestParam("newIdBranch") String newIdBranch, @RequestParam("newIdRoom") String newIdRoom) {

        List<Machinery> machineries = machineryRepository.findByIdBranch(oldIdBranch);

        for (Machinery machinery : machineries) {
            machinery.setIdBranch(newIdBranch);
            machinery.setIdRoom(newIdRoom);
            machineryRepository.save(machinery);
        }

        return ResponseEntity.ok().build();
    }


    // Update machineries by idRoom
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/updateMachineriesByIdRoom", method = RequestMethod.PUT)
    public ResponseEntity<String> updateMachineryByIdRoom(@RequestParam("oldIdRoom") String oldIdRoom, @RequestParam("newIdRoom") String newIdRoom) {

        List<Machinery> machineries = machineryRepository.findByIdRoom(oldIdRoom);

        for (Machinery machinery : machineries) {
            machinery.setIdRoom(newIdRoom);
            machineryRepository.save(machinery);
        }

        return ResponseEntity.ok().build();
    }



}
