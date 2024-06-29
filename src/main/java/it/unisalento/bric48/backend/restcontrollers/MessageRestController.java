package it.unisalento.bric48.backend.restcontrollers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.Message;
import it.unisalento.bric48.backend.dto.MessageDTO;
import it.unisalento.bric48.backend.repositories.MessageRepository;

@RestController
@CrossOrigin
@RequestMapping("/message")
public class MessageRestController {

    @Autowired
    MessageRepository messageRepository;

    // Add a new message
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MessageDTO addMessage(@RequestBody MessageDTO messageDTO) {

        Message newMessage = new Message();
        newMessage.setMessage(messageDTO.getMessage());

        newMessage = messageRepository.save(newMessage);

        messageDTO.setId(newMessage.getId());

        return messageDTO;
    }


    

    //Get all messages
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value="/getAll", method = RequestMethod.GET)
    public List<MessageDTO> getAllMessages() {

        List<MessageDTO> messages = new ArrayList<>();

        for (Message message : messageRepository.findAll()) {

            MessageDTO messageDTO = new MessageDTO();

            messageDTO.setId(message.getId());
            messageDTO.setMessage(message.getMessage());

            messages.add(messageDTO);
        }

        return messages;
    }

    // Delete message by id
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteMessageById(@PathVariable("id") String id) {
    
        messageRepository.deleteById(id);;

        // Verifica se l'entità è stata eliminata con successo
        Optional<Message> deletedEntity = messageRepository.findById(id);
        if (deletedEntity.isPresent()) {
            return ResponseEntity.badRequest().body("ID not found");
        }
        return ResponseEntity.ok().build();
    }

    //Get messages from-to
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value="/getMessagesFromTo", method= RequestMethod.GET)
    public List<MessageDTO> getMessagesFromTo(@RequestParam("from") String from, @RequestParam("to") String to) {

        int c = 1;
        int i = Integer.parseInt(from);
        int j = Integer.parseInt(to);

        List<MessageDTO> messages = new ArrayList<>();

        for(Message message : messageRepository.findAll()) {

            if(c >= i && c <= j){

                MessageDTO messageDTO = new MessageDTO();

                messageDTO.setId(message.getId());
                messageDTO.setMessage(message.getMessage());

                messages.add(messageDTO);
            }

            c++;
        }

        return messages;
    }
    
}
