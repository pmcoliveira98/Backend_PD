package api.entity;

import api.util.COURSE;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String num;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private COURSE course;
    @Column(nullable = false)
    private double classification;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Candidature candidature;

    public Student(String num, String name, String email, COURSE course, double classification) {
        this.num = num;
        this.name = name;
        this.email = email;
        this.course = course;
        this.classification = classification;
    }
}
