package kpuapi.kpulecture;


import com.querydsl.jpa.impl.JPAQueryFactory;
import kpuapi.kpulecture.domain.school.Lecture;
import kpuapi.kpulecture.domain.school.Professor;
import kpuapi.kpulecture.domain.QLecture;
import kpuapi.kpulecture.domain.school.LectureQueryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    @Autowired
    LectureQueryRepository lectureQueryRepository;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
        Professor professor1 = new Professor("professor1");
        Professor professor2 = new Professor("professor2");
        em.persist(professor1);
        em.persist(professor2);
        Lecture lecture1 = new Lecture("lecture1", professor1);
        Lecture lecture2 = new Lecture("lecture2", professor1);
        Lecture lecture3 = new Lecture("lecture3", professor2);
        Lecture lecture4 = new Lecture("lecture4", professor2);
        em.persist(lecture1);
        em.persist(lecture2);
        em.persist(lecture3);
        em.persist(lecture4);
    }

    @Test
    public void startQuerydsl() throws Exception {
        //given
        QLecture l = new QLecture("l");

        //when
        Lecture findLecture = queryFactory
                .select(l)
                .from(l)
                .where(l.lectureName.eq("lecture1"))
                .fetchOne();

        //then
        Assertions.assertThat(findLecture.getLectureName()).isEqualTo("lecture1");
    }

    @Test
    public void findLecturesWithProfessor() throws Exception {
        lectureQueryRepository.findLecturesWithProfessor();
    }
}
