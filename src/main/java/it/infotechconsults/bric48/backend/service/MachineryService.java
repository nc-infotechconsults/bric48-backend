package it.infotechconsults.bric48.backend.service;

import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.Machinery;
import it.infotechconsults.bric48.backend.exception.ResourceAlreadyExists;
import it.infotechconsults.bric48.backend.mapper.MachineryMapper;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.repository.MachineryRepository;
import it.infotechconsults.bric48.backend.rest.dto.MachineryDTO;
import it.infotechconsults.bric48.backend.rest.dto.MachineryResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MachineryService extends AuditService<MachineryDTO, MachineryResponseDTO, Machinery, String> {

    public MachineryService(MachineryRepository repository, EntityManagerRepository<Machinery> eRepository, MachineryMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = Machinery.class;
    }

    @Override
    protected void checkSave(MachineryDTO dto) throws Exception {
        if (repository
                .exists((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("serial"), dto.getSerial()))) {
            throw new ResourceAlreadyExists("Serial already exists");
        }
    }

    @Override
    protected void checkUpdate(String id, MachineryDTO dto, Machinery entity) throws Exception {
        if (repository
                .exists(
                        (root, query, criteriaBuilder) -> criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("serial"), dto.getSerial()),
                                criteriaBuilder.not(criteriaBuilder.equal(root.get("id"), id))))) {
            throw new ResourceAlreadyExists("Serial already exists");
        }
    }

}
