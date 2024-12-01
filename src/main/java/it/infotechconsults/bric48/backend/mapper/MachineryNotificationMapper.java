package it.infotechconsults.bric48.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import it.infotechconsults.bric48.backend.domain.Machinery;
import it.infotechconsults.bric48.backend.domain.MachineryNotification;
import it.infotechconsults.bric48.backend.repository.MachineryRepository;
import it.infotechconsults.bric48.backend.rest.dto.MachineryNotificationDTO;
import it.infotechconsults.bric48.backend.rest.dto.MachineryNotificationResponseDTO;
import jakarta.persistence.EntityNotFoundException;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class MachineryNotificationMapper
        extends BaseMapper<MachineryNotificationDTO, MachineryNotification, MachineryNotificationResponseDTO> {

    @Autowired
    protected MachineryRepository machineryRepository;

    @Override
    @Mapping(target = "machinery", expression = "java(findMachinery(request.getMserial()))")
    @Mapping(target = "solved", constant = "false")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.parse(request.getTimestamp()))")
    public abstract MachineryNotification requestToEntity(MachineryNotificationDTO request) throws Exception;

    protected Machinery findMachinery(String serial) throws Exception {
        return machineryRepository.findBy((root, query, cb) -> cb.equal(root.get("serial"), serial), t -> t.first())
                .orElseThrow(EntityNotFoundException::new);
    }

}
