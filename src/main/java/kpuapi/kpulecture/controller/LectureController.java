package kpuapi.kpulecture.controller;


import kpuapi.kpulecture.controller.form.LectureForm;
import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.repository.LectureRepository;
import kpuapi.kpulecture.repository.ProfessorRepository;
import kpuapi.kpulecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;
    private final LectureRepository lectureRepository;
    private final ProfessorRepository professorRepository;

    @GetMapping("/lectures/new")
    public String createForm(Model model) {
        List<Professor> professors = professorRepository.findAll();
        model.addAttribute("form", new LectureForm());
        model.addAttribute("professors", professors);

        return "lectures/createLectureForm";
    }

    @PostMapping("/lectures/new")
    public String create(@Valid LectureForm form, BindingResult result) {
        if(result.hasErrors()) {
            return "lectures/createLectureForm";
        }
        lectureService.save(form);

        return "redirect:/";
    }

    @GetMapping("/lectures")
    public String list(Model model) {
        List<Lecture> lectures = lectureRepository.findAll();
        model.addAttribute("lectures", lectures);

        return "lectures/lectureList";
    }

    @GetMapping("lectures/{lectureId}/edit")
    public String updateLectureForm(@PathVariable("lectureId") Long lectureId, Model model) {
        Optional<Lecture> lecture = lectureRepository.findById(lectureId);
        List<Professor> professors = professorRepository.findByProfessorName(lecture.get().getProfessor().getProfessorName());

        LectureForm form = new LectureForm();
        form.setId(lectureId);
        form.setLectureCode(lecture.get().getLectureCode());
        form.setLectureName(lecture.get().getLectureName());
        form.setProfessorId(lecture.get().getProfessor().getId());
        form.setLectureSemester(lecture.get().getLectureSemester());
        form.setLectureDate(lecture.get().getLectureDate());
        form.setLectureRoom(lecture.get().getLectureRoom());

        model.addAttribute("form", form);
        model.addAttribute("professors", professors);

        return "lectures/updateLectureForm";
    }

    @PostMapping("lectures/{lectureId}/edit")
    public String updateLecture(@PathVariable("lectureId") Long lectureId, @ModelAttribute("form") LectureForm form) {

        lectureService.updateLecture(lectureId, form);

        return "redirect:/";
    }
}
