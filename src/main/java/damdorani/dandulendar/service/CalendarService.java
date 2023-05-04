package damdorani.dandulendar.service;

import damdorani.dandulendar.domain.Calendar;
import damdorani.dandulendar.domain.CalendarDetail;
import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.dto.CalendarDetailForm;
import damdorani.dandulendar.dto.CalendarForm;
import damdorani.dandulendar.repository.CalendarRepository;
import damdorani.dandulendar.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {
    private final GroupRepository groupRepository;
    private final CalendarRepository calendarRepository;

    // 달력 생성
    @Transactional
    public void craete(CalendarForm calendarForm){
        Group group = groupRepository.findGroup(calendarForm.getGroup_id());
        Calendar calendar = new Calendar(calendarForm.getCal_title(), calendarForm.getColor(), calendarForm.getMemorial_yn(), group);
        calendarRepository.saveCalendar(calendar);
    }

    // 달력 단건 조회
    public Calendar findCalendar(int cal_id){
        return calendarRepository.findCalendar(cal_id);
    }

    // 달력 조회
    public List<Calendar> findCalendarList(){
        return calendarRepository.findCalendarList();
    }

    // 달력 삭제
    @Transactional
    public void deleteCalendar(int calId) {
        Calendar calendar = calendarRepository.findCalendar(calId);
        calendar.updateDelYn("Y");
    }

    // 달력 수정
    @Transactional
    public void update(CalendarForm calendarForm){
        Calendar calendar = calendarRepository.findCalendar(calendarForm.getCal_id());
        calendar.updateCal(calendarForm);
    }






    /**
     * 달력 상세
     */


    // 달력 상세 등록
    @Transactional
    public void craete(CalendarDetailForm calendarDetailForm){
        Calendar calendar = findCalendar(calendarDetailForm.getCal_id());
        CalendarDetail calendarDetail = CalendarDetail.builder()
                .title(calendarDetailForm.getTitle())
                .contents(calendarDetailForm.getContents())
                .start_date(calendarDetailForm.getStart_date())
                .start_time(calendarDetailForm.getStart_time())
                .end_date(calendarDetailForm.getEnd_date())
                .end_time(calendarDetailForm.getEnd_time())
                .repeat_yn(calendarDetailForm.getRepeat_yn())
                .allday_yn(calendarDetailForm.getAllday_yn())
                .del_yn("N")
                .calendar(calendar)
                .build();
        calendarRepository.saveCalendarDetail(calendarDetail);
    }

    // 달력 상세 수정
    @Transactional
    public void updateCalendarDetail(CalendarDetailForm calendarDetailForm){
        Calendar calendar = findCalendar(calendarDetailForm.getCal_id());
        CalendarDetail calendarDetail = calendarRepository.findCalendarDetail(calendarDetailForm.getCal_dtl_id());
        calendarDetail.updateCalendarDetail(calendarDetailForm, calendar);
    }


    // 달력 상세 조회
    public CalendarDetail findCalendarDetail(int cal_dtl_id){
        return calendarRepository.findCalendarDetail(cal_dtl_id);
    }

    // 달력 일정 상세 목록 조회
    public List<Calendar> findCalendarDetailList(){
        List<Calendar> calendarDetailList = calendarRepository.findCalendarDetailList();
        return calendarDetailList;
    }

    // 달력 상세 삭제 (del_yn = 'Y')
    @Transactional
    public void deleteCalendarDetail(int cal_dtl_id){
        CalendarDetail calendarDetail = calendarRepository.findCalendarDetail(cal_dtl_id);
        calendarDetail.updateDelYn("Y");
    }

}
