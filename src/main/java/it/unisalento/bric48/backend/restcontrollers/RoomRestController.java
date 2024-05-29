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

import it.unisalento.bric48.backend.domain.Machinery;
import it.unisalento.bric48.backend.domain.Room;
import it.unisalento.bric48.backend.dto.RoomDTO;
import it.unisalento.bric48.backend.repositories.MachineryRepository;
import it.unisalento.bric48.backend.repositories.NearbyHeadphonesRepository;
import it.unisalento.bric48.backend.repositories.RoomRepository;

@RestController
@CrossOrigin
@RequestMapping("/room")
public class RoomRestController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MachineryRepository machineryRepository;

    @Autowired
    NearbyHeadphonesRepository nearbyHeadphonesRepository;

    // Add a new room
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RoomDTO addRoom(@RequestBody RoomDTO roomDTO) {

        Room newRoom = new Room();
        newRoom.setName(roomDTO.getName());
        newRoom.setIdBranch(roomDTO.getIdBranch());

        newRoom = roomRepository.save(newRoom);

        roomDTO.setId(newRoom.getId());

        return roomDTO;
    }

    //Get rooms by branch
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/find/{idBranch}", method= RequestMethod.GET)
    public List<RoomDTO> getRoomsByIdBranch(@PathVariable("idBranch") String idBranch) {

        List<RoomDTO> rooms = new ArrayList<>();

        for(Room room : roomRepository.findByIdBranch(idBranch)) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(room.getId());
            roomDTO.setName(room.getName());
            roomDTO.setIdBranch(room.getIdBranch());

            rooms.add(roomDTO);
        }

        return rooms;
    }

    //Get rooms by branch from to
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/getRoomsFromTo", method= RequestMethod.GET)
    public List<RoomDTO> getRoomsByIdBranchFromTO(@RequestParam("idBranch") String idBranch, @RequestParam("from") String from, @RequestParam("to") String to) {

        int c = 1;
        int i = Integer.parseInt(from);
        int j = Integer.parseInt(to);

        List<RoomDTO> rooms = new ArrayList<>();

        for(Room room : roomRepository.findByIdBranch(idBranch)) {

            if(c >= i && c <= j){
                RoomDTO roomDTO = new RoomDTO();
                roomDTO.setId(room.getId());
                roomDTO.setName(room.getName());
                roomDTO.setIdBranch(room.getIdBranch());

                rooms.add(roomDTO);
            }

            c++;
        }

        return rooms;
    }

    
    //Get room by id
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/findById/{id}", method= RequestMethod.GET)
    public RoomDTO getRoomById(@PathVariable("id") String id) {

        RoomDTO roomDTO = new RoomDTO();

        Optional<Room> roomOpt = roomRepository.findById(id);

        if(roomOpt.isPresent()){
            Room room = roomOpt.get();

            roomDTO.setId(room.getId());
            roomDTO.setName(room.getName());
            roomDTO.setIdBranch(room.getIdBranch());
        }
        return roomDTO;
    }

    // Delete room by id
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteRoomById(@PathVariable("id") String id) {
  
        roomRepository.deleteById(id);

        // Verifica se l'entità è stata eliminata con successo
        Optional<Room> deletedEntity = roomRepository.findById(id);
        if (deletedEntity.isPresent()) {
            return ResponseEntity.badRequest().body("ID not found");
        }

        List<Machinery> machineries = machineryRepository.findByIdRoom(id);

        for (Machinery machinery : machineries) {
            machinery.setIdRoom("");
            machineryRepository.save(machinery);
        }

        nearbyHeadphonesRepository.deleteByIdRoom(id);

        return ResponseEntity.ok().build();
    }


    // Delete rooms by idBranch
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/deleteByBranch/{idBranch}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteRoomsByIdBranch(@PathVariable("idBranch") String idBranch) {
  
        roomRepository.deleteByIdBranch(idBranch);

        // Verifica se le entità sono state eliminate con successo
        List<Room> deletedEntities = roomRepository.findByIdBranch(idBranch);
        if (!deletedEntities.isEmpty()) {
            return ResponseEntity.badRequest().body("ID not found");
        }

        List<Machinery> machineries = machineryRepository.findByIdBranch(idBranch);

        for (Machinery machinery : machineries) {
            machinery.setIdBranch("");
            machineryRepository.save(machinery);
        }


        return ResponseEntity.ok().build();
    }


    // Update room
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/updateRoom", method = RequestMethod.PUT)
    public ResponseEntity<String> updateRoom(@RequestBody Room editedRoom) {

        Optional<Room> existingRoomOpt = roomRepository.findById(editedRoom.getId());

        Room oldRoom = existingRoomOpt.get();

        if (existingRoomOpt.isPresent()) {
            roomRepository.save(editedRoom);

            List<Machinery> machineries = machineryRepository.findByIdBranch(oldRoom.getIdBranch());

            for (Machinery machinery : machineries) {
                machinery.setIdBranch(editedRoom.getIdBranch());
                machinery.setIdRoom(editedRoom.getId());
                machineryRepository.save(machinery);
            }

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("ID not found");
        }
    }


}
