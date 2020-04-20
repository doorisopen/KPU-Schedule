package kpuapi.kpulecture.controller;


import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.service.LectureService;
import kpuapi.kpulecture.service.ProfessorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;
    private final ProfessorService professorService;

    @GetMapping("/lectures/new")
    public String createForm(Model model) {
        List<Professor> professors = professorService.findProfessors();
        model.addAttribute("form", new LectureForm());
        model.addAttribute("professors", professors);
        return "lectures/createLectureForm";
    }

    @PostMapping("/lectures/new")
    public String create(@RequestParam("professorId") Long professorId,
                         @Valid LectureForm form, BindingResult result) {

        if(result.hasErrors()) {
            return "lectures/createLectureForm";
        }

        Lecture lecture = new Lecture();
        lecture.setLectureCode(form.getLectureCode());
        lecture.setLectureName(form.getLectureName());
        lecture.setSemester(form.getSemester());
        lecture.setLectureDate(form.getLectureDate());
        lecture.setLectureRoom(form.getLectureRoom());

        lectureService.saveLecture(lecture, professorId);

        return "redirect:/";
    }

    @GetMapping("/lectures")
    public String list(Model model) {
        List<Lecture> lectures = lectureService.findLecture();
        model.addAttribute("lectures", lectures);
        return "lectures/lectureList";
    }

}
