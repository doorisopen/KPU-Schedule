package kpuapi.kpulecture.controller;

import kpuapi.kpulecture.domain.Major;
import kpuapi.kpulecture.service.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MajorController {

    private final MajorService majorService;

    @GetMapping("/majors/new")
    public String createForm(Model model) {
        model.addAttribute("form", new MajorForm());
        return "majors/createMajorForm";
    }

    @PostMapping("/majors/new")
    public String create(MajorForm form) {
        Major major = new Major();
        major.setMajorCode(form.getMajorCode());

        majorService.save(major);

        return "redirect:/";
    }

    @GetMapping("/majors")
    public String list(Model model) {
        List<Major> majors = majorService.findMajor();
        model.addAttribute("majors", majors);
        return "majors/majorList";
    }

    @GetMapping("majors/{majorId}/edit")
    public String updateMajorForm(@PathVariable("majorId") Long majorId, Model model) {
        Major major = majorService.findOne(majorId);

        MajorForm form = new MajorForm();
        form.setId(majorId);
        form.setMajorCode(major.getMajorCode());

        model.addAttribute("form", form);
        return "majors/updateMajorForm";
    }

    @PostMapping("majors/{majorId}/edit")
    public String updateMajor(@PathVariable("majorId") Long majorId, @ModelAttribute("form") MajorForm form) {
        majorService.updateMajor(majorId, form.getMajorCode());
        return "redirect:/";
    }

}
