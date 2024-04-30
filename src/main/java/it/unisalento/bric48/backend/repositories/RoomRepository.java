package it.unisalento.bric48.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.Room;

public interface RoomRepository extends MongoRepository<Room, String> {

    public Optional<Room> findById(String id);

    public List<Room> findByIdBranch(String idBranch);

    public void deleteById(String id);

    public void deleteByIdBranch(String idBranch);

}
