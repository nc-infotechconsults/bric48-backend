package it.infotechconsults.bric48.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import it.infotechconsults.bric48.backend.domain.User;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

}
