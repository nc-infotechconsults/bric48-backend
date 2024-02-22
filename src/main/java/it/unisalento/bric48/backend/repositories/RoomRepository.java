package it.unisalento.bric48.backend.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.Room;

public interface RoomRepository extends MongoRepository<Room, String> {

    public Optional<Room> findById(String id);

}
