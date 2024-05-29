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

import it.unisalento.bric48.backend.domain.Branch;
import it.unisalento.bric48.backend.domain.Machinery;
import it.unisalento.bric48.backend.domain.Room;
import it.unisalento.bric48.backend.dto.BranchDTO;
import it.unisalento.bric48.backend.repositories.BranchRepository;
import it.unisalento.bric48.backend.repositories.MachineryRepository;
import it.unisalento.bric48.backend.repositories.NearbyHeadphonesRepository;
import it.unisalento.bric48.backend.repositories.RoomRepository;

@RestController
@CrossOrigin
@RequestMapping("/branch")
public class BranchRestController {

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MachineryRepository machineryRepository;

    @Autowired
    NearbyHeadphonesRepository nearbyHeadphonesRepository;

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

    // Get all branches
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/getAll", method= RequestMethod.GET)
    public List<BranchDTO> getAllBranches() {

        List<BranchDTO> branches = new ArrayList<>();

        for(Branch branch : branchRepository.findAll()) {

            BranchDTO branchDTO = new BranchDTO();

            branchDTO.setId(branch.getId());
            branchDTO.setAddress(branch.getAddress());
            branchDTO.setName(branch.getName());
            branchDTO.setIdAdmin(branch.getIdAdmin());

            branches.add(branchDTO);
        }

        return branches;
    }

    // Get branches from-to
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/getBranchesFromTo", method= RequestMethod.GET)
    public List<BranchDTO> getBranchFromTo(@RequestParam("from") String from, @RequestParam("to") String to) {

        int c = 1;
        int i = Integer.parseInt(from);
        int j = Integer.parseInt(to);

        List<BranchDTO> branches = new ArrayList<>();

        for(Branch branch : branchRepository.findAll()) {

            if(c >= i && c <= j){

                BranchDTO branchDTO = new BranchDTO();

                branchDTO.setId(branch.getId());
                branchDTO.setAddress(branch.getAddress());
                branchDTO.setName(branch.getName());
                branchDTO.setIdAdmin(branch.getIdAdmin());

                branches.add(branchDTO);
            }

            c++;
        }

        return branches;
    }

    //Get branch by id
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/findById/{id}", method= RequestMethod.GET)
    public BranchDTO getBranchById(@PathVariable("id") String id) {

        BranchDTO branchDTO = new BranchDTO();

        Optional<Branch> branchOpt = branchRepository.findById(id);

        if(branchOpt.isPresent()){
            Branch branch = branchOpt.get();

            branchDTO.setId(branch.getId());
            branchDTO.setName(branch.getName());
            branchDTO.setAddress(branch.getAddress());
            branchDTO.setIdAdmin(branch.getIdAdmin());
        }
        return branchDTO;
    }

    // Delete branch by id
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBranchById(@PathVariable("id") String id) {
  
        branchRepository.deleteById(id);

        // Verifica se l'entità è stata eliminata con successo
        Optional<Branch> deletedEntity = branchRepository.findById(id);
        if (deletedEntity.isPresent()) {
            return ResponseEntity.badRequest().body("ID not found");
        }

        roomRepository.deleteByIdBranch(id);

        // Verifica se le entità sono state eliminate con successo
        List<Room> deletedEntities = roomRepository.findByIdBranch(id);
        if (!deletedEntities.isEmpty()) {
            return ResponseEntity.badRequest().body("ID not found");
        }

        List<Machinery> machineries = machineryRepository.findByIdBranch(id);

        for (Machinery machinery : machineries) {
            machinery.setIdBranch("");
            machineryRepository.save(machinery);
        }

        nearbyHeadphonesRepository.deleteByIdBranch(id);

        return ResponseEntity.ok().build();
    }

    // Update branch
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/updateBranch", method = RequestMethod.PUT)
    public ResponseEntity<String> updateBranch(@RequestBody Branch editedBranch) {

        Optional<Branch> existingBranchOpt = branchRepository.findById(editedBranch.getId());

        if (existingBranchOpt.isPresent()) {
            branchRepository.save(editedBranch);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("ID not found");
        }
    }

}
