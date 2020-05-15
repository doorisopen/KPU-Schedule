package kpuapi.kpulecture.domain.school;

import kpuapi.kpulecture.domain.school.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    List<Professor> findByProfessorName(@Param("professorName") String professorName);
}
