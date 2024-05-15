package api.repository;

import api.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    
    @Query("SELECT p FROM Livro p " +
            "LEFT JOIN FETCH p.proposals " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(p.proposals) ASC")
    List<Livro> findAllOrderByProposalsSizeAsc();
}
