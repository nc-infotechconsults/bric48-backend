package it.unisalento.bric48.backend.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

    public Optional<Role> findById(String id);

}
