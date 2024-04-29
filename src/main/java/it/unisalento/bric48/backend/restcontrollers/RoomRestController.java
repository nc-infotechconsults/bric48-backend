package it.unisalento.bric48.backend.restcontrollers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.Room;
import it.unisalento.bric48.backend.dto.RoomDTO;
import it.unisalento.bric48.backend.repositories.RoomRepository;

@RestController
@CrossOrigin
@RequestMapping("/room")
public class RoomRestController {

    @Autowired
    RoomRepository roomRepository;

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


}
