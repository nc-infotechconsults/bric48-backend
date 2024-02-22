package it.unisalento.bric48.backend.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.Headphones;
import it.unisalento.bric48.backend.dto.HeadphonesDTO;
import it.unisalento.bric48.backend.repositories.HeadphonesRepository;

@RestController
@RequestMapping("/headphones")
public class HeadphonesRestController {

    @Autowired
    HeadphonesRepository headphonesRepository;

    // Add a new headphones
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HeadphonesDTO addHeadphones(@RequestBody HeadphonesDTO headphonesDTO) {

        Headphones newHeadphones = new Headphones();
        newHeadphones.setSerial(headphonesDTO.getSerial());

        newHeadphones = headphonesRepository.save(newHeadphones);

        headphonesDTO.setId(newHeadphones.getId());

        return headphonesDTO;
    }

}
