package kpuapi.kpulecture.controller.form;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class ProfessorForm {

    private Long id;

    @NotEmpty(message = "교수 이름은 필수 입니다.")
    private String professorName;
    private String professorMajor;
}
