package it.unisalento.bric48.backend.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.MachineryData;

public interface MachineryDataRepository extends MongoRepository<MachineryData, String>{

    public Optional<MachineryData> findById(String id);
    
}
