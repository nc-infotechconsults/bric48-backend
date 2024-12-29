package it.infotechconsults.bric48.backend.service;

import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.NotificationCode;
import it.infotechconsults.bric48.backend.exception.ResourceAlreadyExists;
import it.infotechconsults.bric48.backend.mapper.NotificationCodeMapper;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.repository.NotificationCodeRepository;
import it.infotechconsults.bric48.backend.rest.dto.NotificationCodeDTO;
import it.infotechconsults.bric48.backend.rest.dto.NotificationCodeResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationCodeService
        extends AuditService<NotificationCodeDTO, NotificationCodeResponseDTO, NotificationCode, String> {

    public NotificationCodeService(NotificationCodeRepository repository,
            EntityManagerRepository<NotificationCode> eRepository, NotificationCodeMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = NotificationCode.class;
    }

    @Override
    protected void checkSave(NotificationCodeDTO dto) throws Exception {
        if (repository.exists((root, query, criteriaBuilder) -> criteriaBuilder
                .equal(criteriaBuilder.lower(root.get("code")), dto.getCode().toLowerCase())))
            throw new ResourceAlreadyExists("Code already exists");
    }

    @Override
    protected void checkUpdate(String id, NotificationCodeDTO dto, NotificationCode entity) throws Exception {
        if (repository.exists((root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("code")), dto.getCode().toLowerCase()),
                criteriaBuilder.not(
                        criteriaBuilder.equal(criteriaBuilder.lower(root.get("id")),
                                id.toLowerCase())))))
            throw new ResourceAlreadyExists("Code already exists");
    }

    @Override
    protected NotificationCode beforeUpdate(NotificationCode entity) throws Exception {
        entity.getTranslations().forEach(t -> t.setNotificationCode(entity));
        return entity;
    }

}
