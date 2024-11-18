package it.infotechconsults.bric48.backend.service;

import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.Structure;
import it.infotechconsults.bric48.backend.mapper.StructureMapper;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.repository.StructureRepository;
import it.infotechconsults.bric48.backend.rest.dto.StructureDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StructureService extends AuditService<StructureDTO, Structure, Structure, String> {

    public StructureService(StructureRepository repository, EntityManagerRepository<Structure> eRepository, StructureMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = Structure.class;
    }

}
