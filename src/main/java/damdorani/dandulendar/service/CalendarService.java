package damdorani.dandulendar.service;

import damdorani.dandulendar.domain.Calendar;
import damdorani.dandulendar.domain.CalendarDetail;
import damdorani.dandulendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;

    // 달력 생성
    @Transactional
    public void craete(Calendar calendar){
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


    // 달력 수정






    /**
     * 달력 상세
     */


    // 달력 상세 등록
    @Transactional
    public void craete(CalendarDetail calendarDetail){
        calendarRepository.saveCalendarDetail(calendarDetail);
    }


    // 달력 상세 조회
    public CalendarDetail findCalendarDetail(int cal_dtl_id){
        return calendarRepository.findCalendarDetail(cal_dtl_id);
    }

    public List<CalendarDetail> findCalendarDetailList(){
        return calendarRepository.findCalendarDetailList();
    }

    // 달력 상세 삭제

    // 달력 상세 수정
}
