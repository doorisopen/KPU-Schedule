package kpuapi.kpulecture.controller;

import kpuapi.kpulecture.domain.MajorCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MajorForm {
    private Long id;
    private MajorCode majorCode;
}
