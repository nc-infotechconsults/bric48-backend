package it.infotechconsults.bric48.backend.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.infotechconsults.bric48.backend.rest.dto.MqttMessageDTO;
import it.infotechconsults.bric48.backend.service.MqttService;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private MqttService mqttService;

    @PostMapping("/machinery/{machineryId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void sendMessage(@RequestBody MqttMessageDTO message, @PathVariable("machineryId") String machineryId) throws Exception {
        mqttService.sendMessageToTopic(String.format("machinery/%s", machineryId), message.getMessage());
    }

}
