package kpuapi.kpulecture.repository;

import kpuapi.kpulecture.domain.Professor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProfessorRepository {

    private final EntityManager em;

    public void save(Professor professor) {
        em.persist(professor);
    }

    public List<Professor> findAll() {
        return em.createQuery("select p from Professor p", Professor.class)
                .getResultList();
    }

    public Professor findOne(Long id) {
        return em.find(Professor.class, id);
    }

    public List<Professor> findByProfessorName(String professorName) {
        return em.createQuery("select p from Professor p where p.professorName = :professorName", Professor.class)
                .setParameter("professorName", professorName)
                .getResultList();
    }
}
