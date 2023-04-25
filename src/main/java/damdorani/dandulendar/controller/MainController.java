package damdorani.dandulendar.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final HttpSession session;

    @GetMapping("/")
    public String login(Model model) {
        Object user = session.getAttribute("user");
        if(user != null){
            return "redirect:/userGroup";
        }

        return "main";
    }

}