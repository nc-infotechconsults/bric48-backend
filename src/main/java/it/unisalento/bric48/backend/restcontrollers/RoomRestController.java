package it.unisalento.bric48.backend.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.Room;
import it.unisalento.bric48.backend.dto.RoomDTO;
import it.unisalento.bric48.backend.repositories.RoomRepository;

@RestController
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

}
