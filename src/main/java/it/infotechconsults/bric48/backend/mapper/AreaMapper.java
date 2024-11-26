package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import it.infotechconsults.bric48.backend.domain.Area;
import it.infotechconsults.bric48.backend.repository.StructureRepository;
import it.infotechconsults.bric48.backend.rest.dto.AreaDTO;
import it.infotechconsults.bric48.backend.rest.dto.AreaResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AreaMapper extends BaseMapper<AreaDTO, Area, AreaResponseDTO> {

    @Autowired
    protected StructureRepository structureRepository;
    
    @Override
    @Mapping(target = "structure", expression = "java(structureRepository.findById(request.getStructureId()).orElse(null))")
    public abstract Area requestToEntity(AreaDTO request);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "structure", expression = "java(structureRepository.findById(request.getStructureId()).orElse(null))")
    public abstract void updateEntity(@MappingTarget Area entity, AreaDTO request);
}
