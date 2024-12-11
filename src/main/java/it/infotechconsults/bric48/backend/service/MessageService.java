package it.infotechconsults.bric48.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.infotechconsults.bric48.backend.domain.Message;
import it.infotechconsults.bric48.backend.mapper.MessageMapper;
import it.infotechconsults.bric48.backend.repository.EntityManagerRepository;
import it.infotechconsults.bric48.backend.repository.MessageRepository;
import it.infotechconsults.bric48.backend.rest.dto.MessageDTO;
import it.infotechconsults.bric48.backend.rest.dto.MessageResponseDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageService extends BaseService<MessageDTO, MessageResponseDTO, Message, String> {

    @Autowired
    private MqttService mqttService;

    public MessageService(MessageRepository repository, EntityManagerRepository<Message> eRepository, MessageMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = Message.class;
    }

    @Override
    protected Message afterSave(Message entity) throws Exception {
        if(entity.getNotification() != null){
            mqttService.sendMessageToTopic(entity.getNotification().getMachinery().getSerial(), entity.getNotification().getDescription());
        }else{
            mqttService.sendMessageToTopic(entity.getReceiver().getHeadphone().getSerial(), entity.getMessage());
        }
        return entity;
    }

}
