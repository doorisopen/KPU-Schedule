package kpuapi.kpulecture.service.school;

import kpuapi.kpulecture.api.dto.LecturesResponseDto;
import kpuapi.kpulecture.api.dto.UsageResponseDto;
import kpuapi.kpulecture.api.dto.UsageUseRequestDto;
import kpuapi.kpulecture.controller.form.LectureForm;
import kpuapi.kpulecture.domain.Usage;
import kpuapi.kpulecture.domain.school.*;
import kpuapi.kpulecture.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final UsageService usageService;

    @Transactional
    public void save(LectureForm form) {
        Lecture lecture = new Lecture(form.getLectureCode(),
                form.getLectureName(),
                form.getLectureSemester(),
                form.getLectureDate(),
                form.getLectureRoom()
                );

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

    public List<LecturesResponseDto> lecturesV1(UsageUseRequestDto requestDto) {
        usageService.use(requestDto.toEntity());
        return lectureRepository.findLectureFetchJoin().stream()
                .map(LecturesResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<LecturesResponseDto> lecturesV2(UsageUseRequestDto requestDto) {
        usageService.use(requestDto.toEntity());
        return lectureQueryRepository.findLectureWithProfessor().stream()
                .map(LecturesResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<LecturesResponseDto> lecturesV3(UsageUseRequestDto requestDto) {
        usageService.use(requestDto.toEntity());
        return lectureQueryRepository.findLecturesWithProfessor_fetchJoin().stream()
                .map(LecturesResponseDto::new)
                .collect(Collectors.toList());
    }
}
