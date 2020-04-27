package kpuapi.kpulecture.repository;

import kpuapi.kpulecture.domain.Major;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MajorJpaRepository {

    private final EntityManager em;

    public void save(Major major) {
        em.persist(major);
    }

    public List<Major> findAll() {
        return em.createQuery("select m from Major m", Major.class)
                .getResultList();
    }

    public List<Major> findByMajorCode(String majorCode) {
        return em.createQuery("select m from Major m where m.majorCode = :majorCode", Major.class)
                .setParameter("majorCode", majorCode)
                .getResultList();
    }

    public Major findOne(Long id) {
        return em.find(Major.class, id);
    }

}
