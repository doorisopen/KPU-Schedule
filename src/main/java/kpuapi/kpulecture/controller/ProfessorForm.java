package kpuapi.kpulecture.controller;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class ProfessorForm {

    @NotEmpty(message = "교수 이름은 필수 입니다.")
    private String name;
    private String major;
}
