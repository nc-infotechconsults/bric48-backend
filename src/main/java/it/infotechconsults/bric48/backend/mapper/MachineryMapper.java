package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import it.infotechconsults.bric48.backend.domain.Machinery;
import it.infotechconsults.bric48.backend.rest.dto.MachineryDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class MachineryMapper extends BaseMapper<MachineryDTO, Machinery, Machinery> {

}
