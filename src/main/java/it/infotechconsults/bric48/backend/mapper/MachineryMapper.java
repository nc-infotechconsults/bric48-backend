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

import it.infotechconsults.bric48.backend.domain.Beacon;
import it.infotechconsults.bric48.backend.domain.Machinery;
import it.infotechconsults.bric48.backend.repository.AreaRepository;
import it.infotechconsults.bric48.backend.repository.BeaconRepository;
import it.infotechconsults.bric48.backend.rest.dto.MachineryDTO;
import it.infotechconsults.bric48.backend.rest.dto.MachineryResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class MachineryMapper extends BaseMapper<MachineryDTO, Machinery, MachineryResponseDTO> {

    @Autowired
    protected AreaRepository areaRepository;

    @Autowired
    protected BeaconRepository beaconRepository;

    @Override
    @Mapping(target = "area", expression = "java(areaRepository.findById(request.getAreaId()).orElse(null))")
    @Mapping(source = "beaconsId", target = "beacons", qualifiedByName = "mapBeaconsId")
    public abstract Machinery requestToEntity(MachineryDTO request);

    @Override
    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "area", expression = "java(areaRepository.findById(request.getAreaId()).orElse(null))")
    @Mapping(target = "beacons", expression = "java(updateMapBeaconsId(entity, request))")
    public abstract void updateEntity(@MappingTarget Machinery entity, MachineryDTO request);

    @Named("mapBeaconsId")
    protected Set<Beacon> mapBeaconsId(Set<String> beaconsId) {
        if(Objects.nonNull(beaconsId) && beaconsId.size() > 0)
            return new HashSet<>(beaconRepository.findAll((root, query, cb) -> root.get("id").in(beaconsId)));
        else
            return new HashSet<>();
    }

    protected Set<Beacon> updateMapBeaconsId(Machinery entity, MachineryDTO request) {
        entity.getBeacons().forEach(x -> x.setMachinery(null));
        if(Objects.nonNull(request.getBeaconsId()) && request.getBeaconsId().size() > 0)
            return new HashSet<>(beaconRepository.findAll((root, query, cb) -> root.get("id").in(request.getBeaconsId())));
        else
            return new HashSet<>();
    }

}
