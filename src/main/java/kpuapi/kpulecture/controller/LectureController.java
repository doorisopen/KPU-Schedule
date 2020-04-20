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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("lectures/{lectureId}/edit")
    public String updateLectureForm(@PathVariable("lectureId") Long lectureId, Model model) {
        Lecture lecture = lectureService.findOne(lectureId);
        List<Professor> professors = professorService.findProfessors();

        LectureForm form = new LectureForm();
        form.setId(lectureId);
        form.setLectureCode(lecture.getLectureCode());
        form.setLectureName(lecture.getLectureName());
        form.setProfessorId(lecture.getProfessor().getId());
        form.setSemester(lecture.getSemester());
        form.setLectureDate(lecture.getLectureDate());
        form.setLectureRoom(lecture.getLectureRoom());

        model.addAttribute("form", form);
        model.addAttribute("professors", professors);

        return "lectures/updateLectureForm";
    }

    @PostMapping("lectures/{lectureId}/edit")
    public String updateLecture(@PathVariable("lectureId") Long lectureId, @ModelAttribute("form") LectureForm form) {
        lectureService.updateLecture(lectureId, form.getProfessorId(), form.getLectureCode(), form.getLectureName(),
                form.getSemester(), form.getLectureDate(), form.getLectureRoom());
        return "redirect:/";
    }
}
