package damdorani.dandulendar.controller;

import damdorani.dandulendar.domain.Calendar;
import damdorani.dandulendar.domain.CalendarDetail;
import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.dto.CalendarDetailForm;
import damdorani.dandulendar.dto.CalendarForm;
import damdorani.dandulendar.dto.SessionUser;
import damdorani.dandulendar.jwt.JwtTokenProvider;
import damdorani.dandulendar.service.CalendarService;
import damdorani.dandulendar.service.GroupService;
import damdorani.dandulendar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CalController {
    private final CalendarService calendarService;
    private final GroupService groupService;

    /**
     * 달력
     * @param model
     * @return
     */
    @GetMapping("/calendars")
    public String calendarList(HttpServletRequest request, Model model){
        /**
         * 그룹 짝이 지어지지 않으면 넘어갈 수 없게 막아야 함
         *
         */


        HttpSession session = request.getSession();
//        model.addAttribute("userInfo", session.getAttribute("user"));
        model.addAttribute("userInfo", new SessionUser(User.builder().build()));

        return "cal/calendarList";
    }

    /**
     * 달력 등록 화면
     * @param groupId
     * @param model
     * @return
     */
    @GetMapping("/calendars/new")
    public String calendarForm(@RequestParam(required = false, defaultValue = "0") int groupId, Model model){
        List<Calendar> calendarList = calendarService.findCalendarList();
        model.addAttribute("calendarList", calendarList);
        return "cal/calendarForm";
    }

    /**
     * 달력 생성
     * @param calendarForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/calendars/new")
    public String createCal(@Valid CalendarForm calendarForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "cal/calendarList";
        }

        calendarService.craete(calendarForm);

        return "redirect:/calendars";
    }

    /**
     * 달력 수정
     * @param calendarForm
     * @param bindingResult
     * @return
     */
    @PutMapping("/calendars/new")
    public String updateCal(@Valid CalendarForm calendarForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "cal/calendarList";
        }

        calendarService.update(calendarForm);

        return "redirect:/calendars";
    }

    /**
     * 달력 일정 상세 삭제
     * @param calId
     * @return
     */
    @PutMapping("/calendars/{calId}")
    public String deleteCalendar(@PathVariable int calId){
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
    public List<Map<String, Object>> calendarsDetailList() throws Exception{
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();

        List<Calendar> calendarDetailList = calendarService.findCalendarDetailList();
        for(Calendar calendar : calendarDetailList){
            hash.put("color", calendar.getColor());
            hash.put("cal_title", calendar.getCal_title());
            for(CalendarDetail calendarDetail : calendar.getCalendars()){
                hash.put("id", calendarDetail.getCal_dtl_id());
                hash.put("title", calendarDetail.getTitle());
                hash.put("start", calendarDetail.getStart_date() + "T" + calendarDetail.getStart_time());
                hash.put("end", getDate(calendarDetail.getEnd_date(), calendarDetail.getAllday_yn()) + "T" + calendarDetail.getEnd_time());
                hash.put("allDay", calendarDetail.getAllday_yn().equals("Y") ? true : false);

                JSONObject jsonObj = new JSONObject(hash);
                jsonArr.add(jsonObj);
            }
        }

        return jsonArr;
    }

    // 종료날짜 +1
    private static String getDate(String date, String allDay) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate endDate = LocalDate.parse(date, formatter);
        return formatter.format(endDate.plusDays("Y".equals(allDay) ? 1 : 0));
    }

    /**
     * 달력 얼정 상세 등록, 수정, 상세 화면
     * @param calDtlId
     * @param model
     * @return
     */
    @GetMapping("/calendars/detail/new")
    public String calendarDetailForm(@RequestParam(required = false, defaultValue = "0") int calDtlId, @RequestParam(required = false) String dateStr, Model model){
        CalendarDetail calendarDetail = null;
        if(calDtlId != 0){
            calendarDetail = calendarService.findCalendarDetail(calDtlId);
            int cal_id = calendarDetail.getCalendar().getCal_id();
            model.addAttribute("cal_id", cal_id);
        }
        List<Calendar> calendarList = calendarService.findCalendarList();

//        model.addAttribute("resultData", calendarDetail);
        model.addAttribute("dateStr", dateStr);
        model.addAttribute("calendarList", calendarList);
        model.addAttribute("calendarDetailForm", calDtlId == 0 ? new CalendarDetailForm() : calendarDetail);

        return "cal/calendarDetailForm";
    }

    /**
     * 달력 일정 상세 등록
     * @param calendarDetailForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/calendars/detail/new")
    public String createCalDetail(@Valid CalendarDetailForm calendarDetailForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "cal/calendarList";
        }

        calendarService.craete(calendarDetailForm);

        return "redirect:/calendars";
    }

    /**
     * 달력 일정 상세 수정
     * @param calendarDetailForm
     * @param bindingResult
     * @return
     */
    @PutMapping("/calendars/detail/new")
    public String updateCalDetail(@Valid CalendarDetailForm calendarDetailForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "cal/calendarList";
        }

        calendarService.updateCalendarDetail(calendarDetailForm);
        return "redirect:/calendars";
    }

    /**
     * 달력 일정 상세 삭제
     * @param calDtlId
     * @return
     */
    @PutMapping("/calendars/detail/{calDtlId}")
    public String deleteCalendarDetail(@PathVariable int calDtlId){
        calendarService.deleteCalendarDetail(calDtlId);
        return "redirect:/calendars";
    }



}
