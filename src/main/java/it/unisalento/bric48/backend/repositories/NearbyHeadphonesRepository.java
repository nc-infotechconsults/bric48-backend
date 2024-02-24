package it.unisalento.bric48.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.NearbyHeadphones;

public interface NearbyHeadphonesRepository extends MongoRepository<NearbyHeadphones, String> {

    public Optional<NearbyHeadphones> findById(String id);

    public List<NearbyHeadphones> findByMserial(String mserial);

}
