package api.service;

import api.entity.Candidature;
import api.entity.Proposal;
import api.entity.Student;
import api.repository.CandidatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatureService {
    private final CandidatureRepository candidatureRepository;

    @Autowired
    public CandidatureService(CandidatureRepository candidatureRepository) {
        this.candidatureRepository = candidatureRepository;
    }

    public List<Candidature> getAll() {
        return candidatureRepository.findAll();
    }

    public Candidature getCandidatureById(Long id) {
        Optional<Candidature> candidatureOptional = candidatureRepository.findById(id);
        return candidatureOptional.orElse(null);
    }

    public Candidature createCandidature(Student student, List<Proposal> proposalList) {
        if (student == null || proposalList.isEmpty()) {
            return null;
        } else {
            Candidature candidature = new Candidature(student, proposalList);
            return candidatureRepository.save(candidature);
        }
    }

    public Candidature updateCandidature(Long id, Boolean usedInAssignment, Student student, List<Proposal> proposalList) {
        Candidature c = getCandidatureById(id);
        if (c == null) {
            return null;
        }

        c.setUsedInAssignment(usedInAssignment);
        c.setStudent(student);
        c.setProposals(proposalList);
        return candidatureRepository.save(c);
    }

    public void deleteCandidature(Long id) {
        candidatureRepository.deleteById(id);
    }

    public List<Candidature> getUnusedCandidatures() {
        return candidatureRepository.findAllUnassignedCandidaturesOrderByStudentClassificationDesc();
    }

    public void save(Candidature candidature) {
        candidatureRepository.save(candidature);
    }
}
