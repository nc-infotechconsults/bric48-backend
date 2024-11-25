package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import it.infotechconsults.bric48.backend.domain.Beacon;
import it.infotechconsults.bric48.backend.rest.dto.BeaconDTO;
import it.infotechconsults.bric48.backend.rest.dto.BeaconResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BeaconMapper extends BaseMapper<BeaconDTO, Beacon, BeaconResponseDTO> {

}
