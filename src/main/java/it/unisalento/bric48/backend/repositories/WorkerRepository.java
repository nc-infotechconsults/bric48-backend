package it.unisalento.bric48.backend.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.Worker;

public interface WorkerRepository extends MongoRepository<Worker, String> {

    public Optional<Worker> findById(String id);

    public Worker findByIdHeadphones(String idHeadphones);

    public Optional<Worker> findByEmail(String email);

    public Optional<Worker> findByRollNumber(String rollNumber);

    public void deleteById(String id);

}
