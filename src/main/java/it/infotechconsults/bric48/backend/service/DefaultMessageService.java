package it.infotechconsults.bric48.backend.service;

import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.DefaultMessage;
import it.infotechconsults.bric48.backend.mapper.DefaultMessageMapper;
import it.infotechconsults.bric48.backend.repository.DefaultMessageRepository;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.rest.dto.DefaultMessageDTO;
import it.infotechconsults.bric48.backend.rest.dto.DefaultMessageResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultMessageService extends AuditService<DefaultMessageDTO, DefaultMessageResponseDTO, DefaultMessage, String> {

    public DefaultMessageService(DefaultMessageRepository repository, EntityManagerRepository<DefaultMessage> eRepository, DefaultMessageMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = DefaultMessage.class;
    }

}
