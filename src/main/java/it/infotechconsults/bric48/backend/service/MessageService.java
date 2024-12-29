package it.infotechconsults.bric48.backend.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.infotechconsults.bric48.backend.domain.Message;
import it.infotechconsults.bric48.backend.domain.NotificationCode;
import it.infotechconsults.bric48.backend.domain.NotificationTranslation;
import it.infotechconsults.bric48.backend.mapper.MessageMapper;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.repository.MessageRepository;
import it.infotechconsults.bric48.backend.repository.NotificationCodeRepository;
import it.infotechconsults.bric48.backend.rest.dto.MessageDTO;
import it.infotechconsults.bric48.backend.rest.dto.MessageResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService extends BaseService<MessageDTO, MessageResponseDTO, Message, String> {

    @Autowired
    private MqttService mqttService;

    @Autowired
    private NotificationCodeRepository notificationCodeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public MessageService(MessageRepository repository, EntityManagerRepository<Message> eRepository,
            MessageMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = Message.class;
    }

    @Override
    protected Message beforeSave(Message entity) throws Exception {
        if (Objects.nonNull(entity.getNotification())) {
            Optional<NotificationCode> notificationCode = notificationCodeRepository.findBy(
                    (root, query, cb) -> cb.equal(root.get("code"), entity.getNotification().getCode()),
                    t -> t.first());
            notificationCode.ifPresentOrElse(t -> {
                Optional<NotificationTranslation> transNot = t.getTranslations().stream()
                        .filter(x -> x.getLanguage().equals(entity.getReceiver().getLanguage()))
                        .findFirst();
                if (!transNot.isPresent()) {
                    transNot = t.getTranslations().stream().filter(x -> x.getIsDefault()).findFirst();
                }

                entity.setMessage(transNot.get().getMessage());
                entity.setLanguage(transNot.get().getLanguage());
            }, () -> {
                throw new EntityNotFoundException();
            });
        }
        return entity;
    }

    @Override
    protected Message afterSave(Message entity) throws Exception {
        if (Objects.nonNull(entity.getReceiver().getHeadphone())) {
            ObjectNode json = objectMapper.createObjectNode();
            json.put("language", entity.getLanguage());
            json.put("message", entity.getMessage());
            mqttService.sendMessageToTopic(entity.getReceiver().getHeadphone().getSerial(), json.toString());
        }
        return entity;
    }

}
