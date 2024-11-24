package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import it.infotechconsults.bric48.backend.domain.Structure;
import it.infotechconsults.bric48.backend.rest.dto.StructureDTO;
import it.infotechconsults.bric48.backend.rest.dto.StructureResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class StructureMapper extends BaseMapper<StructureDTO, Structure, StructureResponseDTO> {

}
