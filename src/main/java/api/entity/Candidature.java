package api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Candidature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "used_in_assignment")
    private boolean usedInAssignment;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // owner
    @Column(nullable = false)
    @ManyToMany
    @JoinTable(
            name = "candidature_proposals",
            joinColumns = @JoinColumn(name = "candidature_id"),
            inverseJoinColumns = @JoinColumn(name = "proposal_id")
    )
    private List<Proposal> proposals;

    public Candidature(Student student, List<Proposal> proposalList) {
        this.student = student;
        this.proposals = proposalList;
        usedInAssignment = false;
    }
}
