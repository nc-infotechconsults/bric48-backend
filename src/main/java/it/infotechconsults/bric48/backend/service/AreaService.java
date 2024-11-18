package it.infotechconsults.bric48.backend.service;

import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.Area;
import it.infotechconsults.bric48.backend.mapper.AreaMapper;
import it.infotechconsults.bric48.backend.repository.AreaRepository;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.rest.dto.AreaDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AreaService extends AuditService<AreaDTO, Area, Area, String> {

    public AreaService(AreaRepository repository, EntityManagerRepository<Area> eRepository, AreaMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = Area.class;
    }

}
