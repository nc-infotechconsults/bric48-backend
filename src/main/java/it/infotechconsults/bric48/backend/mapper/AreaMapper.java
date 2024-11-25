package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import it.infotechconsults.bric48.backend.domain.Area;
import it.infotechconsults.bric48.backend.rest.dto.AreaDTO;
import it.infotechconsults.bric48.backend.rest.dto.AreaResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AreaMapper extends BaseMapper<AreaDTO, Area, AreaResponseDTO> {

}
