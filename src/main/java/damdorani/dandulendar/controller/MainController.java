package damdorani.dandulendar.controller;

import damdorani.dandulendar.domain.User;
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
    private final HttpSession httpSession;
    
    @GetMapping("/")
    public String login(Model model) {
        return "main";
    }

    @GetMapping("/join")
    public String join(Model model){
        User user = User.builder().build();
        model.addAttribute("userInfo", user);

        return "user/join";
    }
}