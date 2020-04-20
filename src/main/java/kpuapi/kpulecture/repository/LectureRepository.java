package kpuapi.kpulecture.repository;

import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.scraping.CrawlingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.Embedded;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRepository {

    private final EntityManager em;

    public void save(Lecture lecture) {
        if(lecture.getId() == null) {
            em.persist(lecture);
        } else {
            em.merge(lecture);
        }
    }

    public List<Lecture> findAll() {
        return em.createQuery("select l from Lecture l", Lecture.class)
                .getResultList();
    }

    public List<Lecture> findAllWithProfessor() {
        return em.createQuery("select l from Lecture l join fetch l.professor p", Lecture.class)
                .getResultList();
    }

    public Lecture findOne(Long id) {
        return em.find(Lecture.class, id);
    }


}
