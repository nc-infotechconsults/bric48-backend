package it.infotechconsults.bric48.backend.service;

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

    public MessageService(MessageRepository repository, EntityManagerRepository<Message> eRepository, MessageMapper mapper) {
        super(repository, eRepository, mapper);
        this.entityClass = Message.class;
    }

}
