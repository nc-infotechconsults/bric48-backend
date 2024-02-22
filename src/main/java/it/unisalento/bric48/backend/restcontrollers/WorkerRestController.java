package it.unisalento.bric48.backend.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.Worker;
import it.unisalento.bric48.backend.dto.WorkerDTO;
import it.unisalento.bric48.backend.repositories.WorkerRepository;

@RestController
@RequestMapping("/worker")
public class WorkerRestController {

    @Autowired
    WorkerRepository workerRepository;

    // Add a new worker
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public WorkerDTO addWorker(@RequestBody WorkerDTO workerDTO) {

        Worker newWorker = new Worker();
        newWorker.setRollNumber(workerDTO.getRollNumber());
        newWorker.setName(workerDTO.getName());
        newWorker.setSurname(workerDTO.getSurname());
        newWorker.setEmail(workerDTO.getEmail());
        newWorker.setPhoneNumber(workerDTO.getPhoneNumber());
        newWorker.setRole(workerDTO.getRole());
        newWorker.setIdHeadphones(workerDTO.getIdHeadphones());
        newWorker.setIdAdmin(workerDTO.getIdAdmin());

        newWorker = workerRepository.save(newWorker);

        workerDTO.setId(newWorker.getId());

        return workerDTO;
    }

}
