package kpuapi.kpulecture.api;

import kpuapi.kpulecture.api.dto.LecturesResponseDto;
import kpuapi.kpulecture.domain.school.Lecture;
import kpuapi.kpulecture.domain.school.LectureQueryRepository;
import kpuapi.kpulecture.domain.school.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LectureApiController {

    private final LectureRepository lectureRepository;
    private final LectureQueryRepository lectureQueryRepository;

    //==JPQL==//
    @GetMapping("/api/v1/lectures")
    public Result lectureV1() {
        List<Lecture> findLectureAllWithProfessor = lectureRepository.findLectureFetchJoin();
        List<LecturesResponseDto> resultList = findLectureAllWithProfessor.stream()
                .map(o -> new LecturesResponseDto(o))
                .collect(Collectors.toList());

        return new Result(HttpStatus.OK, resultList);
    }

    //==Querydsl==//
    @GetMapping("/api/v2/lectures")
    public Result lectureV2() {
        List<Lecture> findLecturesWithProfessor = lectureQueryRepository.findLecturesWithProfessor();
        List<LecturesResponseDto> resultList = findLecturesWithProfessor.stream()
                .map(o -> new LecturesResponseDto(o))
                .collect(Collectors.toList());

        return new Result(HttpStatus.OK, resultList);
    }

}
