package it.infotechconsults.bric48.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.infotechconsults.bric48.backend.domain.Role;

public interface RoleRepository extends JpaRepository<Role, String>{
    
}
