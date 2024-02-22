package it.unisalento.bric48.backend.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.Worker;

public interface WorkerRepository extends MongoRepository<Worker, String> {

    public Optional<Worker> findById(String id);

}
