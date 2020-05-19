package kpuapi.kpulecture.controller;

import kpuapi.kpulecture.controller.form.MajorForm;
import kpuapi.kpulecture.domain.school.Major;
import kpuapi.kpulecture.domain.school.MajorRepository;
import kpuapi.kpulecture.service.school.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MajorController {

    private final MajorService majorService;
    private final MajorRepository majorRepository;

    @GetMapping("/majors/new")
    public String createForm(Model model) {
        model.addAttribute("form", new MajorForm());
        return "majors/createMajorForm";
    }

    @PostMapping("/majors/new")
    public String create(MajorForm form) {
        majorService.save(form);
        return "redirect:/";
    }

    @GetMapping("/majors")
    public String list(Model model) {
        List<Major> majors = majorRepository.findAll();
        model.addAttribute("majors", majors);

        return "majors/majorList";
    }

    @GetMapping("/majors/{majorId}/edit")
    public String updateMajorForm(@PathVariable("majorId") Long majorId, Model model) {
        Optional<Major> major = majorRepository.findById(majorId);

        MajorForm form = new MajorForm();
        form.setId(majorId);
        form.setMajorName(major.get().getMajorName());

        model.addAttribute("form", form);

        return "majors/updateMajorForm";
    }

    @PostMapping("/majors/{majorId}/edit")
    public String updateMajor(@PathVariable("majorId") Long majorId, @ModelAttribute("form") MajorForm form) {
        majorService.changeMajorName(majorId, form.getMajorName());

        return "redirect:/";
    }

}
