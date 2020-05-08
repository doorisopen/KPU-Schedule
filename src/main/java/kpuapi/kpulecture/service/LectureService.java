package kpuapi.kpulecture.service;

import kpuapi.kpulecture.controller.form.LectureForm;
import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.repository.LectureRepository;
import kpuapi.kpulecture.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final ProfessorRepository professorRepository;

    @Transactional
    public void save(LectureForm form) {
        Lecture lecture = new Lecture();
        lecture.setLectureCode(form.getLectureCode());
        lecture.setLectureName(form.getLectureName());
        lecture.setLectureSemester(form.getLectureSemester());
        lecture.setLectureDate(form.getLectureDate());
        lecture.setLectureRoom(form.getLectureRoom());

        //교수 등록
        Optional<Professor> findProfessor = professorRepository.findById(form.getProfessorId());
        lecture.setProfessor(findProfessor.get());

        lectureRepository.save(lecture);
    }

    @Transactional
    public void updateLecture(Long lectureId, LectureForm form) {
        Optional<Lecture> findLecture = lectureRepository.findById(lectureId);
        Optional<Professor> findProfessor = professorRepository.findById(form.getProfessorId());
        findLecture.get().change(form, findProfessor.get());
    }
}
