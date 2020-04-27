package kpuapi.kpulecture.api.dto;

import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.domain.Professor;
import lombok.Getter;

@Getter
public class LectureListResponseDto {

    private Long id;
    private int lectureYear;
    private String lectureSemester;
    private String lectureGubun;

    private String lectureCode;
    private String lectureName;
    private Professor professor;

    private String lectureDate;
    private String lectureRoom;

    public LectureListResponseDto(Lecture entity) {
        this.id = entity.getId();
        this.lectureYear = entity.getLectureYear();
        this.lectureSemester = entity.getLectureSemester();
        this.lectureGubun = entity.getLectureGubun();
        this.lectureCode = entity.getLectureCode();
        this.lectureName = entity.getLectureName();
        this.professor = entity.getProfessor();
        this.lectureDate = entity.getLectureDate();
        this.lectureRoom = entity.getLectureRoom();
    }
}