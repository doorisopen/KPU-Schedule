package kpuapi.kpulecture.service;

import kpuapi.kpulecture.controller.form.LectureForm;
import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.domain.Major;
import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.repository.LectureRepository;
import kpuapi.kpulecture.repository.MajorRepository;
import kpuapi.kpulecture.repository.ProfessorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;


@SpringBootTest
@Transactional
public class LectureServiceTest {

    @Autowired
    LectureService lectureService;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    EntityManager em;

    /**
     * 강의등록에 성공한다.
     * @throws Exception
     */
    @Test
    public void 강의등록() throws Exception {
        //given
        Professor professor = createProfessor();
        LectureForm form = new LectureForm();
        form.setLectureName("스프링부트");
        form.setProfessorId(professor.getId());

        //when
        lectureService.save(form);

        //then
        Assertions.assertThat(form.getLectureName()).isEqualTo("스프링부트");
    }


    /**
     * 강의 수정에 성공한다.
     * @throws Exception
     */
    @Test
    public void 강의_수정_테스트() throws Exception {
        //given
        Professor professor = createProfessor();
        Lecture lecture = new Lecture();
        lecture.setLectureName("스프링부트");
        lecture.setProfessor(professor);
        Lecture saveLecture = lectureRepository.save(lecture);

        LectureForm form = new LectureForm();
        form.setId(saveLecture.getId());
        form.setProfessorId(professor.getId());
        form.setLectureName("JPA");

        //when
        lectureService.updateLecture(saveLecture.getId(), form);

        Optional<Lecture> findLecture = lectureRepository.findById(saveLecture.getId());
        em.flush();
        em.clear();

        //then
        Assertions.assertThat(findLecture.get().getLectureName()).isEqualTo("JPA");
    }

    private Major createMajor() {
        Major major = new Major("컴퓨터공학과");
        return majorRepository.save(major);
    }

    private Professor createProfessor() {
        Professor professor = new Professor("홍길동");
        return professorRepository.save(professor);
    }
}