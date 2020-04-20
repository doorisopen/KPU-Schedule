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

        // 교수 찾기
        Professor professor = professorRepository.findOne(professorId);
        lecture.setProfessor(professor);

        lectureRepository.save(lecture);
        return lecture.getId();
    }

    @Transactional
    public void updateLecture(Long lectureId, String lectureCode, String lectureName, String lecturDate, String lecturRoom) {
        Lecture findLecture = lectureRepository.findOne(lectureId);
        findLecture.change(lectureCode, lectureName, lecturDate, lecturRoom);
    }

    public List<Lecture> findLecture() {
        return lectureRepository.findAll();
    }

    public Lecture findOne(Long lectureId) {
        return lectureRepository.findOne(lectureId);
    }

}
