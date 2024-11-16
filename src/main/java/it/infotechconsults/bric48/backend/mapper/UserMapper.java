package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.rest.dto.UserDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper extends BaseMapper<UserDTO, User, User> {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Override
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
    @Mapping(target = "role.id", source = "roleId")
    public abstract User requestToEntity(UserDTO request);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", expression = "java(request.getPassword() != null ? passwordEncoder.encode(request.getPassword()) : entity.getPassword())")
    public abstract void updateEntity(@MappingTarget User entity, UserDTO request);

}
