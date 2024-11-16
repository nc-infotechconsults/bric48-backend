package it.infotechconsults.bric48.backend.service;

import java.time.Instant;

import it.infotechconsults.bric48.backend.domain.Audit;
import it.infotechconsults.bric48.backend.mapper.BaseMapper;
import it.infotechconsults.bric48.backend.repository.BaseRepository;
import it.infotechconsults.bric48.backend.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AuditService<R, RS, E extends Audit, ID> extends BaseService<R, RS, E, ID> {

    public AuditService(BaseRepository<E, ID> repository, BaseMapper<R, E, RS> mapper) {
        super(repository, mapper);
    }

    @Override
    protected E beforeSaveIntraservice(E entity) {
        entity.setCreatedAt(Instant.now());
        entity.setCreatedBy(SecurityUtil.getCurrentUsername());
        return entity;
    }

    @Override
    protected E beforeUpdateIntraservice(E entity) {
        entity.setUpdatedAt(Instant.now());
        entity.setUpdatedBy(SecurityUtil.getCurrentUsername());
        return entity;
    }

    @Override
    protected E beforeDeleteIntraservice(E entity) {
        entity.setDeletedAt(Instant.now());
        entity.setDeletedBy(SecurityUtil.getCurrentUsername());
        return entity;
    }
}