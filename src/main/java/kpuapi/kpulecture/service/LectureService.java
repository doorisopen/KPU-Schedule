package kpuapi.kpulecture.service;

import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    @Transactional
    public Long saveLecture(Lecture lecture) {
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
