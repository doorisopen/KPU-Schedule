package kpuapi.kpulecture.domain.school;

import kpuapi.kpulecture.domain.school.Lecture;
import kpuapi.kpulecture.domain.school.Major;
import kpuapi.kpulecture.domain.school.Professor;
import kpuapi.kpulecture.domain.school.LectureRepository;
import kpuapi.kpulecture.domain.school.MajorRepository;
import kpuapi.kpulecture.domain.school.ProfessorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class SchoolEntityBasicTest {

    @Autowired
    EntityManager em;
    @Autowired
    MajorRepository majorRepository;
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    ProfessorRepository professorRepository;


    @Test
    public void EntityCreateTest() throws Exception {
        //given
        Major major = new Major("컴퓨터공학과");
        majorRepository.save(major);

        Professor professor = new Professor("홍길동");
        professorRepository.save(professor);

        Lecture lecture = new Lecture();
        lecture.setLectureName("JPA");
        lecture.setProfessor(professor);
        lectureRepository.save(lecture);

        em.flush();
        em.clear();

        //when
        List<Major> findMajor = majorRepository.findAll();
        List<Lecture> findLecture = lectureRepository.findAll();
        List<Professor> findProfessor = professorRepository.findAll();

        //then
        assertThat(findMajor.get(0).getMajorName()).isEqualTo("컴퓨터공학과");
        assertThat(findLecture.get(0).getLectureName()).isEqualTo("JPA");
        assertThat(findProfessor.get(0).getProfessorName()).isEqualTo("홍길동");
    }

    @Test
    public void 연관관계_매핑이_잘되는지_확인() throws Exception {
        //given
        Major major = new Major("컴퓨터공학과");
        majorRepository.save(major);

        Professor professor = new Professor("홍길동");
        professor.setMajor(major);
        professorRepository.save(professor);

        Lecture lecture = new Lecture("JPA 기본편", professor);
        lecture.setMajor(major);
        lectureRepository.save(lecture);

        //when
        List<Lecture> findLecture = lectureRepository.findAll();
        String professorName = findLecture.get(0).getProfessor().getProfessorName();
        String majorName = findLecture.get(0).getMajor().getMajorName();

        //then
        System.out.println("professorName = " + professorName);
        System.out.println("majorName = " + majorName);

        assertThat(professorName).isEqualTo("홍길동");
        assertThat(majorName).isEqualTo("컴퓨터공학과");
    }


}
