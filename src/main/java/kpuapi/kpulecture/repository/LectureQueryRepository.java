package kpuapi.kpulecture.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.domain.QLecture;
import kpuapi.kpulecture.domain.QProfessor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static kpuapi.kpulecture.domain.QLecture.lecture;
import static kpuapi.kpulecture.domain.QProfessor.professor;

@Repository
public class LectureQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    //==의존성 주입==//
    public LectureQueryRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    public List<Lecture> findLecturesWithProfessor() {
        return queryFactory.selectFrom(lecture)
                .join(lecture.professor, professor).fetchJoin()
                .fetch();
    }
}
