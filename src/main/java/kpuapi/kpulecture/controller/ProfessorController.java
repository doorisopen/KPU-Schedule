package kpuapi.kpulecture.controller;

import kpuapi.kpulecture.controller.form.ProfessorForm;
import kpuapi.kpulecture.domain.Major;
import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.repository.MajorRepository;
import kpuapi.kpulecture.repository.ProfessorRepository;
import kpuapi.kpulecture.service.ProfessorService;
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
public class ProfessorController {

    private final ProfessorService professorService;
    private final ProfessorRepository professorRepository;
    private final MajorRepository majorRepository;

    @GetMapping("/professors/new")
    public String createForm(Model model) {
        List<Major> majors = majorRepository.findAll();
        model.addAttribute("form", new ProfessorForm());

        return "professors/createProfessorForm";
    }

    @PostMapping("/professors/new")
    public String create(@Valid ProfessorForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "professors/createProfessorForm";
        }
        Professor professor = new Professor(form.getProfessorName());
        professorRepository.save(professor);

        return "redirect:/";
    }

    @GetMapping("/professors")
    public String list(Model model) {
        List<Professor> professors = professorRepository.findAll();
        model.addAttribute("professors", professors);

        return "professors/professorList";
    }

    @GetMapping("professors/{professorId}/edit")
    public String updateProfessorForm(@PathVariable("professorId") Long professorId, Model model) {
        Optional<Professor> findProfessor = professorRepository.findById(professorId);

        ProfessorForm form = new ProfessorForm();
        form.setId(professorId);
        form.setProfessorName(findProfessor.get().getProfessorName());

        model.addAttribute("form", form);

        return "professors/updateProfessorForm";
    }

    @PostMapping("professors/{professorId}/edit")
    public String updateProfessor(@PathVariable("professorId") Long professorId,
                                  @ModelAttribute("form") ProfessorForm form) {
        professorService.changeProfessorName(professorId, form.getProfessorName());

        return "redirect:/";
    }
}
