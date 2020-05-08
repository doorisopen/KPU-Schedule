package kpuapi.kpulecture.api;

import kpuapi.kpulecture.api.dto.LectureListResponseDto;
import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.repository.LectureQueryRepository;
import kpuapi.kpulecture.repository.LectureRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        List<LectureListResponseDto> collect = findLectureAllWithProfessor.stream()
                .map(o -> new LectureListResponseDto(o))
                .collect(Collectors.toList());

        return new Result(HttpStatus.OK, collect);
    }

    //==Querydsl==//
    @GetMapping("/api/v2/lectures")
    public Result lectureV2() {
        List<Lecture> findLectureAllWithProfessor = lectureQueryRepository.findLecturesWithProfessor();
        List<LectureListResponseDto> collect = findLectureAllWithProfessor.stream()
                .map(o -> new LectureListResponseDto(o))
                .collect(Collectors.toList());

        return new Result(HttpStatus.OK, collect);
    }
    
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private HttpStatus status;
        private T data;
    }
}
