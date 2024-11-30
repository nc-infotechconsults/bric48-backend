package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import it.infotechconsults.bric48.backend.domain.DefaultMessage;
import it.infotechconsults.bric48.backend.rest.dto.DefaultMessageDTO;
import it.infotechconsults.bric48.backend.rest.dto.DefaultMessageResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class DefaultMessageMapper extends BaseMapper<DefaultMessageDTO, DefaultMessage, DefaultMessageResponseDTO> {

}
