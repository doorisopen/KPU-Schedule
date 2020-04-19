package kpuapi.kpulecture.controller;

import kpuapi.kpulecture.domain.Major;
import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.service.MajorService;
import kpuapi.kpulecture.service.ProfessorService;
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
public class ProfessorController {

    private final ProfessorService professorService;
    private final MajorService majorService;

    @GetMapping("/professors/new")
    public String createForm(Model model) {
        List<Major> majors = majorService.findMajor();

        model.addAttribute("form", new ProfessorForm());
        model.addAttribute("majors", majors);

        return "professors/createProfessorForm";
    }

    @PostMapping("/professors/new")
    public String create(@RequestParam("majorId") Long majorId,
                         @Valid ProfessorForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "professors/createProfessorForm";
        }

        Professor professor = new Professor();
        professor.setProfessorName(form.getName());

        professorService.join(professor, majorId);

        return "redirect:/";
    }

    @GetMapping("/professors")
    public String list(Model model) {
        List<Professor> professors = professorService.findProfessors();
        model.addAttribute("professors", professors);
        return "professors/professorList";
    }

}
