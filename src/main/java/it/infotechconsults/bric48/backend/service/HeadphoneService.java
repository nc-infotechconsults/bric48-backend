package it.infotechconsults.bric48.backend.service;

import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.Headphone;
import it.infotechconsults.bric48.backend.exception.ResourceAlreadyExists;
import it.infotechconsults.bric48.backend.mapper.HeadphoneMapper;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.repository.HeadphoneRepository;
import it.infotechconsults.bric48.backend.rest.dto.HeadphoneDTO;
import it.infotechconsults.bric48.backend.rest.dto.HeadphoneResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HeadphoneService extends AuditService<HeadphoneDTO, HeadphoneResponseDTO, Headphone, String> {

    public HeadphoneService(HeadphoneRepository repository, EntityManagerRepository<Headphone> eRepository,
            HeadphoneMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = Headphone.class;
    }

    @Override
    protected void checkSave(HeadphoneDTO dto) throws Exception {
        if (repository
                .exists((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("serial"), dto.getSerial()))) {
            throw new ResourceAlreadyExists("Serial already exists");
        }
    }

    @Override
    protected void checkUpdate(String id, HeadphoneDTO dto, Headphone entity) throws Exception {
        if (repository
                .exists(
                        (root, query, criteriaBuilder) -> criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("serial"), dto.getSerial()),
                                criteriaBuilder.not(criteriaBuilder.equal(root.get("id"), id))))) {
            throw new ResourceAlreadyExists("Serial already exists");
        }
    }
}
