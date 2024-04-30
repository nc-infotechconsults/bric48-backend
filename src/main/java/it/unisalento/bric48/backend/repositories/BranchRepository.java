package it.unisalento.bric48.backend.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.Branch;


public interface BranchRepository extends MongoRepository<Branch, String> {

    public Optional<Branch> findById(String id);

    public void deleteById(String id);

}
