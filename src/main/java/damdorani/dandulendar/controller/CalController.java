package damdorani.dandulendar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CalController {

    @GetMapping("/calendars")
    public String list(Model model){

        return "cal/calendarList";
    }
}
