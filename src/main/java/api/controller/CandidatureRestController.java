package api.controller;

import api.entity.Candidature;
import api.service.CompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/candidatures")
public class CandidatureRestController {
    private final CompositeService compositeService;

    @Autowired
    public CandidatureRestController(CompositeService compositeService) {
        this.compositeService = compositeService;
    }

    @GetMapping
    public List<Candidature> getAll() {
        return compositeService.getAllCandidatures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidature> getCandidatureById(@PathVariable Long id) {
        Candidature candidature = compositeService.getCandidatureById(id);

        if (candidature != null) {
            return ResponseEntity.ok(candidature);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Candidature> createCandidature(
            @RequestParam Long studentId,
            @RequestParam List<Long> proposalsIds
    ) {
        Candidature c = compositeService.createCandidature(studentId, proposalsIds);

        if (c == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCandidature(
            @PathVariable Long id,
            @RequestParam Boolean usedInAssignment,
            @RequestParam Long studentId,
            @RequestParam List<Long> proposalsIds
    ) {
        try {
            Candidature c = compositeService.updateCandidature(id, usedInAssignment, studentId, proposalsIds);
            if (c != null) {
                return ResponseEntity.ok(c);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidature(@PathVariable Long id) {
        compositeService.deleteCandidature(id);
        return ResponseEntity.noContent().build();
    }
}
