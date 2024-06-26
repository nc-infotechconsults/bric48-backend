package it.unisalento.bric48.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.Log;

public interface LogRepository extends MongoRepository<Log, String> {

}
