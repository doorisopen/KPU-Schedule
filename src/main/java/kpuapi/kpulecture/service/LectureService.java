package kpuapi.kpulecture.service;

import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.repository.LectureRepository;
import kpuapi.kpulecture.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final ProfessorRepository professorRepository;

    @Transactional
    public Long saveLecture(Lecture lecture, Long professorId) {

        //교수 찾기
        Professor professor = professorRepository.findOne(professorId);
        lecture.setProfessor(professor);

        lectureRepository.save(lecture);
        return lecture.getId();
    }

    @Transactional
    public void updateLecture(Long lectureId, Long professorId, String lectureCode,
                              String lectureName, int semester, String lectureDate, String lectureRoom) {
        //강의 찾기
        Lecture findLecture = lectureRepository.findOne(lectureId);

        //교수 찾기
        Professor findProfessor = professorRepository.findOne(professorId);

        //강의 수정
        findLecture.change(lectureCode, lectureName, findProfessor, semester, lectureDate, lectureRoom);
    }

    // N + 1 발생 -> join fetch 로 성능 개선...
    public List<Lecture> findLecture() {
        return lectureRepository.findAll();
    }

    public List<Lecture> findLectureWithProfessor() {
        return lectureRepository.findAllWithProfessor();
    }

    public Lecture findOne(Long lectureId) {
        return lectureRepository.findOne(lectureId);
    }

}
