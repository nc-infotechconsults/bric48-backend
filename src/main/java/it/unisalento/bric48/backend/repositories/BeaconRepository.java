package it.unisalento.bric48.backend.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.Beacon;

public interface BeaconRepository extends MongoRepository<Beacon, String> {

    public Optional<Beacon> findById(String id);

}
