package it.infotechconsults.bric48.backend.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

import it.infotechconsults.bric48.backend.domain.Message;
import it.infotechconsults.bric48.backend.domain.User;
import it.infotechconsults.bric48.backend.repository.MachineryNotificationRepository;
import it.infotechconsults.bric48.backend.repository.UserRepository;
import it.infotechconsults.bric48.backend.rest.dto.MessageDTO;
import it.infotechconsults.bric48.backend.rest.dto.MessageResponseDTO;
import it.infotechconsults.bric48.backend.util.SecurityUtil;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class MessageMapper extends BaseMapper<MessageDTO, Message, MessageResponseDTO> {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MachineryNotificationRepository machineryNotificationRepository;

    @Override
    @Mapping(target = "sentAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "sender", expression = "java(setSenderUser().orElse(null))")
    @Mapping(target = "receiver", expression = "java(userRepository.findById(request.getReceiverId()).orElse(null))")
    @Mapping(target = "notification", expression = "java(request.getNotificationId() != null ? machineryNotificationRepository.findById(request.getNotificationId()).orElse(null) : null)")
    public abstract Message requestToEntity(MessageDTO request);
    
    protected Optional<User> setSenderUser() {
        return userRepository.findBy((root, query, cb) -> cb.equal(root.get("email"), SecurityUtil.getCurrentUsername()), t -> t.first());
    }

}
