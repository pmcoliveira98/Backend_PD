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
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String autor;
    @Column(nullable = false)
    private String categoria;
    @Column(nullable = false)
    private String edicao;

    @OneToMany(mappedBy = "livro")
    private List<Proposal> proposals;


    public Livro(String nome, String autor,String categoria,String edicao) {
        this.nome = nome;
        this.autor = autor;
        this.categoria = categoria;
        this.edicao = edicao;
    }

    public void addProposal(Proposal proposal) {
        proposals.add(proposal);
    }

    public void removeProposal(Proposal proposal) {
        proposals.remove(proposal);
    }
}
