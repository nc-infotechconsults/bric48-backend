package it.infotechconsults.bric48.backend.service;

import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.Role;
import it.infotechconsults.bric48.backend.mapper.RoleMapper;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.repository.RoleRepository;
import it.infotechconsults.bric48.backend.rest.dto.RoleDTO;
import it.infotechconsults.bric48.backend.rest.dto.RoleResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleService extends AuditService<RoleDTO, RoleResponseDTO, Role, String> {

    public RoleService(RoleRepository repository, EntityManagerRepository<Role> eRepository, RoleMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = Role.class;
    }

}
