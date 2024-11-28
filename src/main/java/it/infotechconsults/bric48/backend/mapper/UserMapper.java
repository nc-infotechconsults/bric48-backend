package it.infotechconsults.bric48.backend.mapper;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.infotechconsults.bric48.backend.domain.Machinery;
import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.repository.HeadphoneRepository;
import it.infotechconsults.bric48.backend.repository.MachineryRepository;
import it.infotechconsults.bric48.backend.repository.RoleRepository;
import it.infotechconsults.bric48.backend.rest.dto.UserDTO;
import it.infotechconsults.bric48.backend.rest.dto.UserResponseDTO;
import it.infotechconsults.bric48.backend.rest.dto.UserSessionDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper extends BaseMapper<UserDTO, User, UserResponseDTO> {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected HeadphoneRepository headphoneRepository;

    @Autowired
    protected MachineryRepository machineryRepository;

    @Override
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
    @Mapping(target = "role", expression = "java(roleRepository.findById(request.getRoleId()).orElse(null))")
    @Mapping(target = "headphone", expression = "java(headphoneRepository.findById(request.getHeadphoneId()).orElse(null))")
    @Mapping(source = "machineriesId", target = "machineries", qualifiedByName = "mapMachineriesId")
    public abstract User requestToEntity(UserDTO request);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", expression = "java(request.getPassword() != null && !request.getPassword().isBlank() ? passwordEncoder.encode(request.getPassword()) : entity.getPassword())")
    @Mapping(target = "role", expression = "java(roleRepository.findById(request.getRoleId()).orElse(null))")
    @Mapping(target = "headphone", expression = "java(headphoneRepository.findById(request.getHeadphoneId()).orElse(null))")
    @Mapping(source = "machineriesId", target = "machineries", qualifiedByName = "mapMachineriesId")
    public abstract void updateEntity(@MappingTarget User entity, UserDTO request);

    public abstract UserSessionDTO entityToSession(User entity);

    @Named("mapMachineriesId")
    protected Set<Machinery> mapMachineriesId(Set<String> machineriesId) {
        if(Objects.nonNull(machineriesId) && machineriesId.size() > 0)
            return new HashSet<>(machineryRepository.findAll((root, query, cb) -> root.get("id").in(machineriesId)));
        else
            return new HashSet<>();
    }

}
