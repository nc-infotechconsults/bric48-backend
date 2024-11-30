package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import it.infotechconsults.bric48.backend.domain.MachineryNotification;
import it.infotechconsults.bric48.backend.rest.dto.MachineryNotificationDTO;
import it.infotechconsults.bric48.backend.rest.dto.MachineryNotificationResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class MachineryNotificationMapper extends BaseMapper<MachineryNotificationDTO, MachineryNotification, MachineryNotificationResponseDTO> {

}
