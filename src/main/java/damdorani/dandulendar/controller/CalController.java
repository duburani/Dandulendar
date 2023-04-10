package damdorani.dandulendar.controller;

import damdorani.dandulendar.domain.Calendar;
import damdorani.dandulendar.domain.CalendarDetail;
import damdorani.dandulendar.dto.CalendarDetailForm;
import damdorani.dandulendar.dto.CalendarForm;
import damdorani.dandulendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CalController {
    private final CalendarService calendarService;

    @GetMapping("/calendars")
    public String list(Model model){

        model.addAttribute("calendarForm", new CalendarForm());

        List<Calendar> calendarList = calendarService.findCalendarList();
        model.addAttribute("calendarList", calendarList);

        return "cal/calendarList";
    }

    @PostMapping("/calendars/new")
    public String createCal(@Valid CalendarForm calendarForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "cal/calendarList";
        }

        Calendar calendar = new Calendar(calendarForm.getCal_title(), calendarForm.getColor(), calendarForm.getMemorial_yn(), calendarForm.getGroup_id());
        calendarService.craete(calendar);

        return "redirect:/calendars";
    }



    @PostMapping("/calendars/detail/new")
    public String createCalDetail(@Valid CalendarDetailForm calendarDetailForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "cal/calendarList";
        }

        CalendarDetail calendarDetail = CalendarDetail.builder()
                .title(calendarDetailForm.getTitle())
                .contents(calendarDetailForm.getContents())
                .start_date(calendarDetailForm.getStart_date())
                .start_time(calendarDetailForm.getStart_time())
                .end_date(calendarDetailForm.getEnd_date())
                .end_time(calendarDetailForm.getEnd_time())
                .repeat_yn(calendarDetailForm.getRepeat_yn())
                .allday_yn(calendarDetailForm.getAllday_yn())
                .cal_id(calendarDetailForm.getCal_id())
                .build();
        calendarService.craete(calendarDetail);

//        Calendar calendar = new Calendar(calendarForm.getCal_title(), calendarForm.getColor(), calendarForm.getMemorial_yn(), calendarForm.getGroup_id());
//        calendarService.craete(calendar);

        return "redirect:/calendars";
    }



}
