package it.unisalento.bric48.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.Machinery;

public interface MachineryRepository extends MongoRepository<Machinery, String> {

    public Optional<Machinery> findById(String id);

    public Machinery findByMserial(String mserial);

    public List<Machinery> findByIdRoom(String idRoom);

    public void deleteByMserial(String mserial);

}
