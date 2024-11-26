package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import it.infotechconsults.bric48.backend.domain.Headphone;
import it.infotechconsults.bric48.backend.rest.dto.HeadphoneDTO;
import it.infotechconsults.bric48.backend.rest.dto.HeadphoneResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class HeadphoneMapper extends BaseMapper<HeadphoneDTO, Headphone, HeadphoneResponseDTO> {

}
