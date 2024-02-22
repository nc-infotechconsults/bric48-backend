package it.unisalento.bric48.backend.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.Branch;
import it.unisalento.bric48.backend.dto.BranchDTO;
import it.unisalento.bric48.backend.repositories.BranchRepository;

@RestController
@RequestMapping("/branch")
public class BranchRestController {

    @Autowired
    BranchRepository branchRepository;

    // Add a new branch
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BranchDTO addBranch(@RequestBody BranchDTO branchDTO) {

        Branch newBranch = new Branch();
        newBranch.setName(branchDTO.getName());
        newBranch.setAddress(branchDTO.getAddress());
        newBranch.setIdAdmin(branchDTO.getIdAdmin());

        newBranch = branchRepository.save(newBranch);

        branchDTO.setId(newBranch.getId());

        return branchDTO;
    }

}
