package kpuapi.kpulecture.api.dto;

import kpuapi.kpulecture.domain.school.Lecture;
import kpuapi.kpulecture.domain.school.Professor;
import lombok.Getter;

@Getter
public class LecturesResponseDto {

    private Long lectureId;
    private int lectureYear;
    private String lectureSemester;
    private String lectureGubun;

    private String lectureCode;
    private String lectureName;
    private String professorName;

    private String lectureDate;
    private String lectureRoom;

    public LecturesResponseDto(Lecture entity) {
        this.lectureId = entity.getId();
        this.lectureYear = entity.getLectureYear();
        this.lectureSemester = entity.getLectureSemester();
        this.lectureGubun = entity.getLectureGubun();
        this.lectureCode = entity.getLectureCode();
        this.lectureName = entity.getLectureName();
        this.professorName = entity.getProfessor().getProfessorName();
        this.lectureDate = entity.getLectureDate();
        this.lectureRoom = entity.getLectureRoom();
    }
}