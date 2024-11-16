package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.rest.dto.UserDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper extends BaseMapper<UserDTO, User, User> {

}
