package damdorani.dandulendar.service;

import damdorani.dandulendar.domain.Calendar;
import damdorani.dandulendar.domain.CalendarDetail;
import damdorani.dandulendar.domain.ColorCode;
import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.dto.*;
import damdorani.dandulendar.repository.CalendarDetailRepository;
import damdorani.dandulendar.repository.CalendarJpaRepository;
import damdorani.dandulendar.repository.CalendarRepository;
import damdorani.dandulendar.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {
//    private final GroupJpaRepository groupJpaRepository;
    private final CalendarJpaRepository calendarJpaRepository;

    private final GroupService groupService;
    private final CalendarRepository calendarRepository;
    private final CalendarDetailRepository calendarDetailRepository;

    /*
    내가 지켜야 할 원칙

    1. Controller는 웹 전달만 하는 계층
        비즈니스 로직은 서비스에서 처리할 수 있도록 하자.

    2. Service 에서 DTO <-> Entity 변환의 역할을 맡도록 하자.
        그렇지 않으면 LazyInitializationException 노출 위험이 커짐
        트랜잭션 안에서 하이버네이트의 명령이 이루어질 수 있도록 하자.

    3. 서비스 안에서 다른 서비스 호출 가능
        캘린더에서 그룹조회를 원한다면 당연히 그룹서비스를 불러 호출
        그룹 레파지토리 ㄴㄴ
        --> 이를 위해 dto 개념이 명확해야 할듯..

     4. RequestDTO / ResponseDto 구분
        요청과 응답 그리고 엔티티의 경계는 확실히 해두자.

     5. list 조회시 page, slice로 받을 수 있도록 변경하자.중
     */


    // 달력 생성
    @Transactional
    public void craete(CalendarForm calendarForm){
        Group group = groupService.findGroup(calendarForm.getGroup_id());
        Calendar calendar = Calendar.builder()
                .cal_title(calendarForm.getCal_title())
                .color(calendarForm.getColor())
                .memorial_yn(calendarForm.getMemorial_yn())
                .group(group)
                .build();
        calendarRepository.save(calendar);
    }

    // 달력 단건 조회
    public CalendarResponseDto findCalendar(Long cal_id){
        Calendar calendar = calendarRepository.findById(cal_id).orElse(null);
        return new CalendarResponseDto(calendar);
    }

    // 달력 조회
    public List<CalendarResponseDto> findCalendarList(Long groupId){
        Group group = groupService.findGroup(groupId);
        List<Calendar> calendarByGroup = calendarRepository.findCalendarByGroup(group);
        return calendarByGroup.stream().map(CalendarResponseDto::new).collect(Collectors.toList());
    }

    // 달력 삭제
    @Transactional
    public void deleteCalendar(Long calId) {
        Calendar calendar = calendarRepository.findById(calId).orElse(null);
        if(calendar != null)
            calendar.updateDelYn("Y");
    }

    // 달력 수정
    @Transactional
    public void update(CalendarForm calendarForm){
        Calendar calendar = calendarRepository.findById(calendarForm.getCal_id()).orElse(null);
        if(calendar != null)
            calendar.updateCal(calendarForm);
    }






    /**
     * 달력 상세
     */


    // 달력 상세 등록
    @Transactional
    public void craete(CalendarDetailForm calendarDetailForm){
        Calendar calendar = calendarRepository.findById(calendarDetailForm.getCal_id()).orElse(null);
        CalendarDetail calendarDetail = CalendarDetail.builder()
                .title(calendarDetailForm.getTitle())
                .contents(calendarDetailForm.getContents())
                .start_date(calendarDetailForm.getStart_date())
                .start_time(calendarDetailForm.getStart_time())
                .end_date(calendarDetailForm.getEnd_date())
                .end_time(calendarDetailForm.getEnd_time())
                .start_full_date(CommonUtils.convertTime(calendarDetailForm.getStart_date(), calendarDetailForm.getStart_time()))
                .end_full_date(CommonUtils.convertTime(calendarDetailForm.getEnd_date(), calendarDetailForm.getEnd_time()))
                .repeat_yn(calendarDetailForm.getRepeat_yn())
                .allday_yn(calendarDetailForm.getAllday_yn())
                .calendar(calendar)
//                .del_yn("N")
                .build();
        calendarDetailRepository.save(calendarDetail);
    }

    // 달력 상세 수정
    @Transactional
    public void updateCalendarDetail(CalendarDetailForm calendarDetailForm){
        CalendarDetail calendarDetail = calendarDetailRepository.findById(calendarDetailForm.getCal_dtl_id()).orElse(null);
        if(calendarDetail != null) {
            Calendar calendar = calendarRepository.findById(calendarDetailForm.getCal_id()).orElse(null);
            calendarDetail.updateCalendarDetail(calendarDetailForm, calendar);
        }
    }

    // 달력 상세 조회
    public CalendarDetailResponseDto findCalendarDetail(Long cal_dtl_id){
        CalendarDetail calendarDetail = calendarDetailRepository.findById(cal_dtl_id).orElse(null);
        return new CalendarDetailResponseDto(calendarDetail);
    }

    // 달력 일정 상세 목록 조회
    public JSONArray findCalendarDetailList(CalendarRequestDto calendarRequestDto) throws ParseException {
        List<Calendar> calendarDetailList = calendarJpaRepository.findCalendarDetailList(calendarRequestDto);
        JSONArray jsonArr = new JSONArray();
        HashMap<String, Object> hash = new HashMap<>();
        for(Calendar calendar : calendarDetailList){
            hash.put("color", ColorCode.valueOf(calendar.getColor().toUpperCase()).getRgb_color());
            hash.put("cal_title", calendar.getCal_title());
            for(CalendarDetail calendarDetail : calendar.getCalendars()){
                hash.put("id", calendarDetail.getCal_dtl_id());
                hash.put("title", calendarDetail.getTitle());
                hash.put("start", calendarDetail.getStart_date() + "T" + calendarDetail.getStart_time());
                hash.put("end", getDate(calendarDetail.getEnd_date(), calendarDetail.getAllday_yn()) + "T" + calendarDetail.getEnd_time());
                hash.put("allDay", "Y".equals(calendarDetail.getAllday_yn()) ? true : false);

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

    // 달력 상세 삭제 (del_yn = 'Y')
    @Transactional
    public void deleteCalendarDetail(Long cal_dtl_id){
        CalendarDetail calendarDetail = calendarDetailRepository.findById(cal_dtl_id).orElse(null);
        if (calendarDetail != null)
            calendarDetail.updateDelYn("Y");
    }

}
