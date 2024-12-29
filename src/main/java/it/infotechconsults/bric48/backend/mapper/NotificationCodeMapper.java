package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import it.infotechconsults.bric48.backend.domain.NotificationCode;
import it.infotechconsults.bric48.backend.rest.dto.NotificationCodeDTO;
import it.infotechconsults.bric48.backend.rest.dto.NotificationCodeResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class NotificationCodeMapper extends BaseMapper<NotificationCodeDTO, NotificationCode, NotificationCodeResponseDTO> {

}
