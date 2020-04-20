package kpuapi.kpulecture.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class LectureForm {

    @NotEmpty(message = "강의 코드는 필수 입니다.")
    private String lectureCode;

    @NotEmpty(message = "강의명은 필수 입니다.")
    private String lectureName;

    private int semester;
    private String lectureDate;
    private String lectureRoom;
}
