package kpuapi.kpulecture.api;

import kpuapi.kpulecture.api.dto.LecturesResponseDto;
import kpuapi.kpulecture.domain.school.Lecture;
import kpuapi.kpulecture.domain.school.LectureQueryRepository;
import kpuapi.kpulecture.domain.school.LectureRepository;
import kpuapi.kpulecture.service.school.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LectureApiController {

    private final LectureService lectureService;

    //==JPQL==//
    @GetMapping("/api/v1/lectures")
    public Result lecturesV1() {
        return new Result(HttpStatus.OK, lectureService.lecturesV1());
    }

    //==Querydsl==//
    @GetMapping("/api/v2/lectures")
    public Result lecturesV2() {
        return new Result(HttpStatus.OK, lectureService.lecturesV2());
    }

}
