package kpuapi.kpulecture.service;

import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.repository.LectureRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LectureServiceTest {

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    LectureService lectureService;

    @Autowired
    EntityManager em;

    @Test
    public void 강의등록() throws Exception {
        //given
        Lecture lecture = new Lecture();
        lecture.setLectureName("스프링부트");

        //when
        Long saveId = lectureService.saveLecture(lecture);

        //then
        assertEquals(lecture, lectureRepository.findOne(saveId));
    }

    @Test
    public void 강의_수정_테스트() throws Exception {
        //given
        Lecture lecture = new Lecture();
        lecture.setLectureName("스프링부트");
        Long saveId = lectureService.saveLecture(lecture);

        //when
        Lecture findLecture = lectureService.findOne(saveId);
        lectureService.updateLecture(findLecture.getId(), "x", "JPA", "123", "aab");

        //then
        Assert.assertEquals("수정 완료", "JPA", lecture.getLectureName());
    }



}