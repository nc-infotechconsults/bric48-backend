package it.unisalento.bric48.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.Headphones;

public interface HeadphonesRepository extends MongoRepository<Headphones, String> {

    public Optional<Headphones> findById(String id);

    public void deleteBySerial(String serial);

    public Optional<Headphones> findBySerial(String serial);

    public List<Headphones> findByIsAssociated(String isAssociated);
}
