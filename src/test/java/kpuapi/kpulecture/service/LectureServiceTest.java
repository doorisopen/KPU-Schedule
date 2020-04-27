package kpuapi.kpulecture.service;

import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.domain.Major;
import kpuapi.kpulecture.domain.MajorCode;
import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.repository.LectureJpaRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LectureServiceTest {

    @Autowired
    LectureJpaRepository lectureJpaRepository;
    @Autowired LectureService lectureService;
    @Autowired MajorService majorService;
    @Autowired ProfessorService professorService;
    @Autowired EntityManager em;

    /**
     * 강의등록에 성공한다.
     * @throws Exception
     */
    @Test
    public void 강의등록() throws Exception {
        //given
        Long majorId = createMajor();
        Long professorId = createProfessor(majorId);

        Lecture lecture = new Lecture();
        lecture.setLectureName("스프링부트");

        //when
        Long saveId = lectureService.save(lecture, professorId);

        //then
        assertEquals(lecture, lectureJpaRepository.findOne(saveId));
    }


    /**
     * 강의 수정에 성공한다.
     * @throws Exception
     */
    @Test
    public void 강의_수정_테스트() throws Exception {
        //given
        Long majorId = createMajor();
        Long professorId = createProfessor(majorId);

        Lecture lecture = new Lecture();
        lecture.setLectureName("스프링부트");
        Long saveId = lectureService.save(lecture, professorId);

        //when
        Lecture findLecture = lectureService.findOne(saveId);
        lectureService.updateLecture(findLecture.getId(), professorId, "123", "JPA", "2020", "x", "x");

        //then
        Assert.assertEquals("수정 완료", "JPA", lecture.getLectureName());
    }

    private Long createMajor() {
        Major major = new Major();
        major.setMajorCode(MajorCode.ACS);
        return majorService.save(major);
    }

    private Long createProfessor(Long majorId) {
        Professor professor = new Professor();
        professor.setProfessorName("홍길동");
        return professorService.join(professor, majorId);
    }
}