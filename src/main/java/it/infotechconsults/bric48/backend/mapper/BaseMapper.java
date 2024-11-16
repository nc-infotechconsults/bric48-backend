package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface BaseMapper<R, E, RS> {
    E requestToEntity(R request);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget E entity, R request);

    RS entityToResponse(E entity);
}
