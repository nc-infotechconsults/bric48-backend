package it.infotechconsults.bric48.backend.service;

import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.Headphone;
import it.infotechconsults.bric48.backend.mapper.HeadphoneMapper;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.repository.HeadphoneRepository;
import it.infotechconsults.bric48.backend.rest.dto.HeadphoneDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HeadphoneService extends AuditService<HeadphoneDTO, Headphone, Headphone, String> {

    public HeadphoneService(HeadphoneRepository repository, EntityManagerRepository<Headphone> eRepository, HeadphoneMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = Headphone.class;
    }

}
