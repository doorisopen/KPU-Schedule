package kpuapi.kpulecture.service.school;

import kpuapi.kpulecture.api.dto.LecturesResponseDto;
import kpuapi.kpulecture.controller.form.LectureForm;
import kpuapi.kpulecture.domain.school.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureQueryRepository lectureQueryRepository;
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

    public List<LecturesResponseDto> lecturesV1() {
        return lectureRepository.findLectureFetchJoin().stream()
                .map(LecturesResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<LecturesResponseDto> lecturesV2() {
        return lectureQueryRepository.findLecturesWithProfessor().stream()
                .map(LecturesResponseDto::new)
                .collect(Collectors.toList());
    }
}
