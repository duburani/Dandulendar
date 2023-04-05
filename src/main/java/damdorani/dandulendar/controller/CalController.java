package damdorani.dandulendar.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalController {

    @GetMapping("/calendars")
    public String list(Model model){

        return "cal/calendarList";
    }
}
