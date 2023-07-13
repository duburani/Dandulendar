package damdorani.dandulendar.controller;

import damdorani.dandulendar.dto.SessionUser;
import damdorani.dandulendar.oauth2.LoginUser;
import damdorani.dandulendar.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @GetMapping("/")
    public String login(Model model, @LoginUser SessionUser sessionUser) {
        String returnUrl = mainService.returnUrlByUserGroup(sessionUser);
        return "main".equals(returnUrl) ? returnUrl : "redirect:/" + returnUrl;
    }
}