package it.unisalento.bric48.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.unisalento.bric48.backend.domain.Admin;

public interface AdminRepository extends MongoRepository<Admin, String> {

    public Admin findByEmail(String email);

}
