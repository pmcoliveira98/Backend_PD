package api.service;


import api.entity.Proposal;
import api.repository.ProposalRepository;
import api.util.COURSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProposalService {
    private final ProposalRepository proposalRepository;

    @Autowired
    public ProposalService(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    public List<Proposal> getAll() {
        return proposalRepository.findAll();
    }

    public Proposal getProposalById(Long id) {
        Optional<Proposal> proposalOptional = proposalRepository.findById(id);
        return proposalOptional.orElse(null);
    }

    public Proposal createProposal(String title, String description, String companyName, COURSE course) {
        Proposal proposal = new Proposal(title, description, companyName, course);
        return proposalRepository.save(proposal);
    }

    public Proposal updateProposal(Long id, String title, String description, String companyName, COURSE course, String studentNumber) {
        Proposal p = getProposalById(id);
        if (p == null) 
            return null;
        
        if (title == null || description == null || companyName == null || course == null || studentNumber == null) {
            throw new RuntimeException("Parameters cannot be null.");
        }

        p.setTitle(title);
        p.setDescription(description);
        p.setCompanyName(companyName);
        p.setCourse(course);
        p.setStudentNumber(studentNumber);
        return proposalRepository.save(p);
    }

    public void deleteProposal(Long id) {
        proposalRepository.deleteById(id);
    }

    public void save(Proposal cp) {
        proposalRepository.save(cp);
    }
}
