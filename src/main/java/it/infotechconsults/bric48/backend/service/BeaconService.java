package it.infotechconsults.bric48.backend.service;

import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.Beacon;
import it.infotechconsults.bric48.backend.mapper.BeaconMapper;
import it.infotechconsults.bric48.backend.repository.BeaconRepository;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.rest.dto.BeaconDTO;
import it.infotechconsults.bric48.backend.rest.dto.BeaconResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeaconService extends AuditService<BeaconDTO, BeaconResponseDTO, Beacon, String> {

    public BeaconService(BeaconRepository repository, EntityManagerRepository<Beacon> eRepository, BeaconMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = Beacon.class;
    }

}
