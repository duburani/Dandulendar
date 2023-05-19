package damdorani.dandulendar.controller;

import damdorani.dandulendar.domain.*;
import damdorani.dandulendar.dto.*;
import damdorani.dandulendar.service.CalendarService;
import damdorani.dandulendar.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat.ISO;

@Controller
@RequiredArgsConstructor
public class CalController {
    private final CalendarService calendarService;
    private final GroupService groupService;
    private final HttpSession session;

    /**
     * 달력
     * @param model
     * @return
     */
    @GetMapping("/calendars")
    public String calendarList(Model model) throws ParseException {
        Object objUser = session.getAttribute("user");
        if(objUser == null){
            return "redirect:/";
        }else{
            SessionUser sessionUser = (SessionUser) objUser;
            String userId = sessionUser.getUser_id();
            List<UserGroupResponse> ugList = groupService.findGroupByUserId(userId);

            if(ugList.size() < 1){
                return "redirect:/userGroup";
            }

            int dDays = this.calcMemorialDate(ugList.get(0).getMemorial_date());

            model.addAttribute("userInfo", sessionUser);
            model.addAttribute("ugList", ugList);
            model.addAttribute("dDays", dDays);

        }
        return "cal/calendarList";
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
    public String calendarForm(@RequestParam int groupId, Model model){
        List<CalendarResponse> calendarList = calendarService.findCalendarList(groupId);
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
    public List<Map<String, Object>> calendarsDetailList(CalendarRequest calendarRequest) throws Exception

    {
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();

        List<Calendar> calendarDetailList = calendarService.findCalendarDetailList(calendarRequest);
        for(Calendar calendar : calendarDetailList){
            hash.put("color", ColorCode.valueOf(calendar.getColor().toUpperCase()).getRgb_color());
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
    public String calendarDetailForm(@RequestParam(required = false, defaultValue = "0") int calDtlId
                                    , @RequestParam(required = false) String dateStr
                                    , @RequestParam int groupId
                                    , Model model){
        CalendarDetail calendarDetail = null;
        if(calDtlId != 0){
            calendarDetail = calendarService.findCalendarDetail(calDtlId);
            int cal_id = calendarDetail.getCalendar().getCal_id();
            model.addAttribute("cal_id", cal_id);
        }
        List<CalendarResponse> calendarList = calendarService.findCalendarList(groupId);

//        model.addAttribute("resultData", calendarDetail);
        model.addAttribute("dateStr", dateStr);
        model.addAttribute("groupId", groupId);
        model.addAttribute("calendarList", calendarList);
        model.addAttribute("calendarDetailForm", calDtlId == 0 ? new CalendarDetailForm() : calendarDetail);

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
    public String deleteCalendarDetail(@PathVariable int calDtlId){
        calendarService.deleteCalendarDetail(calDtlId);
        return "redirect:/calendars";
    }
}
