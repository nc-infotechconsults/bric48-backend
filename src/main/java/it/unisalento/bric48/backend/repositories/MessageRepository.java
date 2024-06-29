package it.unisalento.bric48.backend.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.Message;

public interface MessageRepository extends MongoRepository<Message, String>{

    public Optional<Message> findById(String id);

    public void deleteById(String id);

}
