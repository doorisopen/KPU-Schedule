package kpuapi.kpulecture.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MajorForm {
    private Long id;

    @NotEmpty(message = "전공명은 필수 입니다.")
    private String majorName;
    private String majorCode;
}
