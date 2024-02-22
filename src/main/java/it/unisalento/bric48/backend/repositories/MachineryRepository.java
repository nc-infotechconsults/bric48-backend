package it.unisalento.bric48.backend.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.Machinery;

public interface MachineryRepository extends MongoRepository<Machinery, String> {

    public Optional<Machinery> findById(String id);

}
