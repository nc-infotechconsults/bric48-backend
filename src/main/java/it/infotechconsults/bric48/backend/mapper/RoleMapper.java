package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import it.infotechconsults.bric48.backend.domain.Role;
import it.infotechconsults.bric48.backend.rest.dto.RoleDTO;
import it.infotechconsults.bric48.backend.rest.dto.RoleResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class RoleMapper extends BaseMapper<RoleDTO, Role, RoleResponseDTO> {

}
