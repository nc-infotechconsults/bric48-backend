package it.infotechconsults.bric48.backend.service;

import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.Machinery;
import it.infotechconsults.bric48.backend.mapper.MachineryMapper;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.repository.MachineryRepository;
import it.infotechconsults.bric48.backend.rest.dto.MachineryDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MachineryService extends AuditService<MachineryDTO, Machinery, Machinery, String> {

    public MachineryService(MachineryRepository repository, EntityManagerRepository<Machinery> eRepository, MachineryMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = Machinery.class;
    }

}
