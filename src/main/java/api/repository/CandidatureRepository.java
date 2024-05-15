package api.repository;

import api.entity.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
   
    @Query("SELECT c FROM Candidature c JOIN c.student s WHERE c.usedInAssignment = false ORDER BY s.classification DESC")
    List<Candidature> findAllUnassignedCandidaturesOrderByStudentClassificationDesc();
}
