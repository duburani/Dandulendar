package damdorani.dandulendar.controller;

import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.dto.*;
import damdorani.dandulendar.oauth2.LoginUser;
import damdorani.dandulendar.service.CalendarService;
import damdorani.dandulendar.service.GroupService;
import damdorani.dandulendar.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CalController {
    private final CalendarService calendarService;
    private final GroupService groupService;
    private final MainService mainService;

    /**
     * 달력
     * @param model
     * @return
     */
    @GetMapping("/calendars")
    public String calendarList(Model model, @LoginUser SessionUser sessionUser) throws ParseException {
        String returnUrl = mainService.returnUrlByUserGroup(sessionUser);
        if("calendars".equals(returnUrl)){

            Group group = groupService.findGroup(sessionUser.getGroup_id());

            int dDays = this.calcMemorialDate(group.getMemorial_date());

            model.addAttribute("userInfo", sessionUser);
            model.addAttribute("group", new GroupResponseDto(group));
            model.addAttribute("dDays", dDays);

            returnUrl = "cal/calendarList";
        }
        return "cal/calendarList".equals(returnUrl) ? returnUrl : "redirect:/" + returnUrl;
    }

    /**
     * 기념일 계산
     * @param memorialDate
     * @return
     */
    private int calcMemorialDate(String memorialDate) throws ParseException {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(dateFormat.parse(memorialDate).getTime());
        Date todayDate = new Date(dateFormat.parse(today).getTime());

        long calculate = todayDate.getTime() - date.getTime();

        int dDays = (int) (calculate / (24*60*60*1000));

        return dDays;
    }


    /**
     * 달력 등록 화면
     * @param groupId
     * @param model
     * @return
     */
    @GetMapping("/calendars/new")
    public String calendarForm(@RequestParam Long groupId, Model model){
        List<CalendarResponseDto> calendarList = calendarService.findCalendarList(groupId);
        model.addAttribute("calendarList", calendarList);
        model.addAttribute("groupId", groupId);
        return "cal/calendarForm";
    }

    /**
     * 달력 생성
     * @param calendarForm
     * @return
     */
    @PostMapping("/calendars/new")
    public String createCal(@Valid CalendarForm calendarForm){
        calendarService.craete(calendarForm);
        return "redirect:/calendars";
    }

    /**
     * 달력 수정
     * @param calendarForm
     * @return
     */
    @PutMapping("/calendars/new")
    public String updateCal(@Valid CalendarForm calendarForm){
        calendarService.update(calendarForm);
        return "redirect:/calendars";
    }

    /**
     * 달력 일정 상세 삭제
     * @param calId
     * @return
     */
    @PutMapping("/calendars/{calId}")
    public String deleteCalendar(@PathVariable Long calId){
        calendarService.deleteCalendar(calId);
        return "redirect:/calendars";
    }












    /**
     * detail
     */




    /**
     * 달력 일정 상세 목록 화면
     * @return
     * @throws Exception
     */
    @GetMapping("/calendars/detail")
    @ResponseBody
    public List<Map<String, Object>> calendarsDetailList(CalendarRequestDto calendarRequestDto) throws Exception

    {
        return calendarService.findCalendarDetailList(calendarRequestDto);
    }


    /**
     * 달력 얼정 상세 등록, 수정, 상세 화면
     * @param calDtlId
     * @param model
     * @return
     */
    @GetMapping("/calendars/detail/new")
    public String calendarDetailForm(@RequestParam(required = false) Long calDtlId
                                    , @RequestParam(required = false) String dateStr
                                    , @RequestParam Long groupId
                                    , Model model){
        CalendarDetailResponseDto calendarDetail = null;
        if(calDtlId != null){
            calendarDetail = calendarService.findCalendarDetail(calDtlId);
            Long cal_id = calendarDetail.getCalendar().getCal_id();
            model.addAttribute("cal_id", cal_id);
        }
        List<CalendarResponseDto> calendarList = calendarService.findCalendarList(groupId);

//        model.addAttribute("resultData", calendarDetail);
        model.addAttribute("dateStr", dateStr);
        model.addAttribute("groupId", groupId);
        model.addAttribute("calendarList", calendarList);
        model.addAttribute("calendarDetailForm", calDtlId == null ? new CalendarDetailForm() : calendarDetail);

        return "cal/calendarDetailForm";
    }

    /**
     * 달력 일정 상세 등록
     * @param calendarDetailForm
     * @return
     */
    @PostMapping("/calendars/detail/new")
    public String createCalDetail(@Valid CalendarDetailForm calendarDetailForm){
        calendarService.craete(calendarDetailForm);
        return "redirect:/calendars";
    }

    /**
     * 달력 일정 상세 수정
     * @param calendarDetailForm
     * @return
     */
    @PutMapping("/calendars/detail/new")
    public String updateCalDetail(@Valid CalendarDetailForm calendarDetailForm){
        calendarService.updateCalendarDetail(calendarDetailForm);
        return "redirect:/calendars";
    }

    /**
     * 달력 일정 상세 삭제
     * @param calDtlId
     * @return
     */
    @PutMapping("/calendars/detail/{calDtlId}")
    public String deleteCalendarDetail(@PathVariable Long calDtlId){
        calendarService.deleteCalendarDetail(calDtlId);
        return "redirect:/calendars";
    }
}
