package it.infotechconsults.bric48.backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import it.infotechconsults.bric48.backend.mapper.BaseMapper;
import it.infotechconsults.bric48.backend.repository.BaseRepository;
import it.infotechconsults.bric48.backend.repository.specification.SpecificationBuilder;
import it.infotechconsults.bric48.backend.rest.dto.FiltersDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public abstract class BaseService<R, RS, E, ID> {

    protected final BaseRepository<E, ID> repository;
    protected final BaseMapper<R, E, RS> mapper;

    // methods called for business logic
    protected void checkSave(R dto) throws Exception {
    }

    protected E beforeSave(E entity) {
        return entity;
    }

    protected E afterSave(E entity) {
        return entity;
    }

    protected void checkUpdate(ID id, R dto, E entity) throws Exception {
    }

    protected E beforeUpdate(E entity) {
        return entity;
    }

    protected E afterUpdate(E entity) {
        return entity;
    }

    protected void checkDelete(ID id, E entity) throws Exception {
    }

    protected E beforeDelete(E entity) {
        return entity;
    }

    protected void afterDelete(E entity) {
    }

    // method called for intermediate business logic
    protected E beforeSaveIntraservice(E entity) {
        return entity;
    }

    protected E afterSaveIntraservice(E entity) {
        return entity;
    }

    protected E beforeUpdateIntraservice(E entity) {
        return entity;
    }

    protected E afterUpdateIntraservice(E entity) {
        return entity;
    }

    protected E beforeDeleteIntraservice(E entity) {
        return entity;
    };

    protected E afterDeleteIntraservice(E entity) {
        return entity;
    };

    @Transactional(rollbackOn = Exception.class)
    public E save(R dto) throws Exception {
        try {
            checkSave(dto);
            E entity = mapper.requestToEntity(dto);
            entity = beforeSave(entity);
            entity = beforeSaveIntraservice(entity);
            E newEntity = repository.saveAndFlush(entity);
            newEntity = afterSaveIntraservice(newEntity);
            newEntity = afterSave(newEntity);
            return newEntity;
        } catch (Exception e) {
            log.error("Error happen during save(): ", e.getMessage());
            log.debug("Error stacktrace: ", e);
            throw e;
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public E update(ID id, R dto) throws Exception {
        try {
            E entityDb = repository.findById(id).orElseThrow(EntityNotFoundException::new);
            checkUpdate(id, dto, entityDb);
            mapper.updateEntity(entityDb, dto);
            E entity = beforeUpdate(entityDb);
            entity = beforeUpdateIntraservice(entity);
            E newEntity = repository.saveAndFlush(entity);
            newEntity = afterUpdateIntraservice(newEntity);
            newEntity = afterUpdate(newEntity);
            return newEntity;
        } catch (Exception e) {
            log.error("Error happen during update(): ", e.getMessage());
            log.debug("Error stacktrace: ", e);
            throw e;
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void delete(ID id) throws Exception {
        try {
            E entityDb = repository.findById(id).orElseThrow(EntityNotFoundException::new);
            checkDelete(id, entityDb);
            E entity = beforeDelete(entityDb);
            entity = beforeDeleteIntraservice(entity);
            E newEntity = repository.saveAndFlush(entity);
            entity = afterDeleteIntraservice(entity);
            afterDelete(newEntity);
        } catch (Exception e) {
            log.error("Error happen during delete(): ", e.getMessage());
            log.debug("Error stacktrace: ", e);
            throw e;
        }
    }

    public E getById(ID id) throws Exception {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Page<E> search(FiltersDTO filters, Pageable pageable) throws Exception {
        SpecificationBuilder<E> specBuilder = new SpecificationBuilder<>();
        Specification <E> spec = specBuilder.buildSpecification(filters);
        if(spec != null)
            return repository.findAll(spec, pageable);
        else
            return repository.findAll(pageable);
    }
}