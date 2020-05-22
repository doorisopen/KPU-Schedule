package kpuapi.kpulecture.controller;

import kpuapi.kpulecture.config.auth.LoginUser;
import kpuapi.kpulecture.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@Slf4j
public class HomeController {

    private final HttpSession httpSession;

    @RequestMapping("/")
    public String home(Model model, @LoginUser SessionUser user) {
        return "home";
    }
}
