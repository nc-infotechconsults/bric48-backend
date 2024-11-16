package it.infotechconsults.bric48.backend.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public abstract class BaseMapper<R, E, RS> {
    public abstract E requestToEntity(R request);
    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntity(@MappingTarget E entity, R request);
    public abstract RS entityToResponse(E entity);
    public abstract List<RS> entitiesToResponses(List<E> entity);

    public Page<RS> pageEntityToPageRespoonse(Page<E> page){
        List<RS> list = entitiesToResponses(page.getContent());
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }
}
