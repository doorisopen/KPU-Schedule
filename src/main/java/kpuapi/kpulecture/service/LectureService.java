package kpuapi.kpulecture.service;

import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.repository.LectureJpaRepository;
import kpuapi.kpulecture.repository.ProfessorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {

    private final LectureJpaRepository lectureJpaRepository;
    private final ProfessorJpaRepository professorJpaRepository;

    @Transactional
    public Long save(Lecture lecture, Long professorId) {

        //교수 찾기
        Professor professor = professorJpaRepository.findOne(professorId);
        lecture.setProfessor(professor);

        lectureJpaRepository.save(lecture);
        return lecture.getId();
    }

    @Transactional
    public void updateLecture(Long lectureId, Long professorId, String lectureCode,
                              String lectureName, String semester, String lectureDate, String lectureRoom) {
        //강의 찾기
        Lecture findLecture = lectureJpaRepository.findOne(lectureId);

        //교수 찾기
        Professor findProfessor = professorJpaRepository.findOne(professorId);

        //강의 수정
        findLecture.change(lectureCode, lectureName, findProfessor, semester, lectureDate, lectureRoom);
    }

    // N + 1 발생 -> join fetch 로 성능 개선...
    public List<Lecture> findLecture() {
        return lectureJpaRepository.findAll();
    }

    public List<Lecture> findLectureWithProfessor() {
        return lectureJpaRepository.findAllWithProfessor();
    }

    public Lecture findOne(Long lectureId) {
        return lectureJpaRepository.findOne(lectureId);
    }

}
