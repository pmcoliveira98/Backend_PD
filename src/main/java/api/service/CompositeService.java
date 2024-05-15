package api.service;

import api.entity.Candidature;
import api.entity.Livro;
import api.entity.Proposal;
import api.entity.Student;
import api.util.COURSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompositeService {
    final CandidatureService candidatureService;
    final LivroService livroService;
    final ProposalService proposalService;
    final StudentService studentService;

    @Autowired
    public CompositeService(CandidatureService candidatureService, LivroService livroService, ProposalService proposalService, StudentService studentService) {
        this.candidatureService = candidatureService;
        this.livroService = livroService;
        this.proposalService = proposalService;
        this.studentService = studentService;
    }


   
    @Transactional
    public int assign() {
        List<Candidature> candidatures = candidatureService.getUnusedCandidatures();
        List<Livro> livroList = livroService.getLivrosOrderByProposalsSize();
        int assigned = 0;
        int livrosIndex = 0;

        for (Candidature candidature : candidatures) {
            candidature.setUsedInAssignment(true);
            List<Proposal> candidatureProposals = candidature.getProposals();
            if (candidatureProposals != null && !candidatureProposals.isEmpty()) {
                for (Proposal cp : candidatureProposals) {
                   
                    if (cp.getStudentNumber() == null) {
                        Student student = candidature.getStudent();
                        if (student.getCourse() == cp.getCourse()) {
                           
                            cp.setStudentNumber(student.getNum());

                            Livro livro = livroList.get(livrosIndex);
                            cp.setLivro(livro);
                            livro.addProposal(cp);

                           
                            livrosIndex = (livrosIndex + 1) % livroList.size();

                            saveToDatabase(candidature, cp, livro);

                            assigned++;
                        
                            break;
                        }
                    }
                }
            }
        }
        return assigned;
    }

    
    private void saveToDatabase(Candidature candidature, Proposal cp, Livro livro) {
        proposalService.save(cp);
        livroService.save(livro);
        candidatureService.save(candidature);
    }

    public List<Proposal> fetchAndAddProposalsToList(List<Long> proposalsIds) {
        List<Proposal> proposalList = new ArrayList<>();
        for (Long propId : proposalsIds) {
            Proposal prop = proposalService.getProposalById(propId);
            if (prop == null) {
                throw new RuntimeException("Invalid proposal id " + propId);
            }
            proposalList.add(prop);
        }
        return proposalList;
    }


    
    public List<Proposal> getAllProposals() {
        return proposalService.getAll();
    }

    public Proposal getProposalById(Long id) {
        return proposalService.getProposalById(id);
    }

    public Proposal createProposal(String title, String description, String companyName, COURSE course) {
        return proposalService.createProposal(title, description, companyName, course);
    }

    public Proposal updateProposal(Long id, String title, String description, String companyName, COURSE course, String studentNumber) {
        return proposalService.updateProposal(id, title, description, companyName, course, studentNumber);
    }

    public void deleteProposal(Long id) {
        proposalService.deleteProposal(id);
    }


    
    public List<Candidature> getAllCandidatures() {
        return candidatureService.getAll();
    }

    public Candidature getCandidatureById(Long id) {
        return candidatureService.getCandidatureById(id);
    }

    public Candidature createCandidature(Long studentId, List<Long> proposalsIds) {
        Student student = studentService.getStudentById(studentId);
        List<Proposal> proposalList = new ArrayList<>(proposalsIds.size());

        for (long propId : proposalsIds) {
            Proposal p = proposalService.getProposalById(propId);
            proposalList.add(p);
        }
        return candidatureService.createCandidature(student, proposalList);
    }

    public Candidature updateCandidature(Long id, Boolean usedInAssignment, Long studentId, List<Long> proposalsIds) {
        if (usedInAssignment == null || studentId == null || proposalsIds == null) {
            throw new RuntimeException("Parameters cannot be null.");
        }

        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("Invalid student id " + studentId);
        }
        List<Proposal> proposalList = fetchAndAddProposalsToList(proposalsIds);
        return candidatureService.updateCandidature(id, usedInAssignment, student, proposalList);
    }

    public void deleteCandidature(Long id) {
        candidatureService.deleteCandidature(id);
    }


    // -------------- Livro -------------- //
    public List<Livro> getAllLivros() {
        return livroService.getAll();
    }

    public Livro getLivroById(Long id) {
        return livroService.getLivroById(id);
    }

    public Livro createLivro(String nome, String autor,String categoria,String edicao) {
        return livroService.createLivro(nome, autor,categoria,edicao);
    }

    public Livro updateLivro(Long id, String nome, String autor,String categoria,String edicao, List<Long> proposalsIds) {
        if (nome == null || autor == null || categoria == null || edicao == null || proposalsIds == null) {
            throw new RuntimeException("Parameters cannot be null.");
        }

        List<Proposal> proposalList = fetchAndAddProposalsToList(proposalsIds);
        return livroService.updateLivro(id, nome, autor,categoria,edicao, proposalList);
    }

    public void deleteLivro(Long id) {
        livroService.deleteLivro(id);
    }


    
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    public Student getStudentById(Long id) {
        return studentService.getStudentById(id);
    }

    public Student createStudent(String num, String name, String email, COURSE course, double classification) {
        return studentService.createStudent(num, name, email, course, classification);
    }

    public Student updateStudent(Long id, String num, String name, String email, COURSE course, Double classification) {
        return studentService.updateStudent(id, num, name, email, course, classification);
    }

    public void deleteStudent(Long id) {
        studentService.deleteStudent(id);
    }
}