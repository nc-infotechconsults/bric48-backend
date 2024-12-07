package it.infotechconsults.bric48.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.MachineryNotification;
import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.mapper.MachineryNotificationMapper;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.repository.MachineryNotificationRepository;
import it.infotechconsults.bric48.backend.rest.dto.MachineryNotificationDTO;
import it.infotechconsults.bric48.backend.rest.dto.MachineryNotificationResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MachineryNotificationService
        extends BaseService<MachineryNotificationDTO, MachineryNotificationResponseDTO, MachineryNotification, String> {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public MachineryNotificationService(MachineryNotificationRepository repository,
            EntityManagerRepository<MachineryNotification> eRepository, MachineryNotificationMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = MachineryNotification.class;
    }

    @Override
    protected MachineryNotification afterSave(MachineryNotification entity) throws Exception {
        for (User x : entity.getMachinery().getUsers()) {
            simpMessagingTemplate.convertAndSend("/notification" + x.getId(), mapper.entityToResponse(entity));
        }

        simpMessagingTemplate.convertAndSend("/notification/administrators", mapper.entityToResponse(entity));

        return entity;
    }

}
