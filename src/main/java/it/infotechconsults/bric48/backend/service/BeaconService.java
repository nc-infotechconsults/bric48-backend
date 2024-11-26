package it.infotechconsults.bric48.backend.service;

import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.Beacon;
import it.infotechconsults.bric48.backend.exception.ResourceAlreadyExists;
import it.infotechconsults.bric48.backend.mapper.BeaconMapper;
import it.infotechconsults.bric48.backend.repository.BeaconRepository;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.rest.dto.BeaconDTO;
import it.infotechconsults.bric48.backend.rest.dto.BeaconResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BeaconService extends AuditService<BeaconDTO, BeaconResponseDTO, Beacon, String> {

    public BeaconService(BeaconRepository repository, EntityManagerRepository<Beacon> eRepository,
            BeaconMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = Beacon.class;
    }

    @Override
    protected void checkSave(BeaconDTO dto) throws Exception {
        if (repository
                .exists((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("serial"), dto.getSerial()))) {
            throw new ResourceAlreadyExists("Serial already exists");
        }
    }

    @Override
    protected void checkUpdate(String id, BeaconDTO dto, Beacon entity) throws Exception {
        if (repository
                .exists(
                    (root, query, criteriaBuilder) -> criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("serial"), dto.getSerial()),
                            criteriaBuilder.not(criteriaBuilder.equal(root.get("id"), id))
                        )
                    )
            ) {
            throw new ResourceAlreadyExists("Serial already exists");
        }
    }

}
