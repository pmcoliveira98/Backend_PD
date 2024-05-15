package api.controller;

import api.entity.Proposal;
import api.service.CompositeService;
import api.util.COURSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/proposals")
public class ProposalRestController {
    private final CompositeService compositeService;

    @Autowired
    public ProposalRestController(CompositeService compositeService) {
        this.compositeService = compositeService;
    }

    @GetMapping
    public List<Proposal> getAllProposals() {
        return compositeService.getAllProposals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proposal> getProposalById(@PathVariable Long id) {
        Proposal proposal = compositeService.getProposalById(id);
        if (proposal != null) {
            return ResponseEntity.ok(proposal);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Proposal> createProposal(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String companyName,
            @RequestParam COURSE course
    ) {
        Proposal p = compositeService.createProposal(title, description, companyName, course);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProposal(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String companyName,
            @RequestParam COURSE course,
            @RequestParam String studentNumber
    ) {
        try {
            Proposal p = compositeService.updateProposal(id, title, description, companyName, course, studentNumber);
            if (p != null) {
                return ResponseEntity.ok(p);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProposal(@PathVariable Long id) {
        compositeService.deleteProposal(id);
        return ResponseEntity.noContent().build();
    }

    // check for candidatures and assign students to proposals
    @PostMapping("/assign")
    public ResponseEntity<String> assign() {
        int assigned = compositeService.assign();
        String response;
        if (assigned > 0) {
            response = "Assigned " + assigned + " proposals.";
        } else {
            response = "No new assignments were made. All candidatures have already been used or all proposals are already assigned to a student.";
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
