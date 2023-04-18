package damdorani.dandulendar.domain;

import damdorani.dandulendar.dto.CalendarDetailForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name="CalendarDetail")
@Builder
@AllArgsConstructor
public class CalendarDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cal_dtl_id;
    private String title;
    private String contents;
    private String start_date;
    private String start_time;
    private String end_date;
    private String end_time;
    private String repeat_yn;
    private String allday_yn;
    private String regr_id;
    private String modr_id;
    private String del_yn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cal_id")
    private Calendar calendar;

    protected CalendarDetail() {

    }

    /**
     * 달력 일정 수정
     */
    public void updateCalendarDetail(CalendarDetailForm calendarDetailForm, Calendar calendar){
        this.title = calendarDetailForm.getTitle();
        this.contents = calendarDetailForm.getContents();
        this.start_date = calendarDetailForm.getStart_date();
        this.start_time = calendarDetailForm.getStart_time();
        this.end_date = calendarDetailForm.getEnd_date();
        this.end_time = calendarDetailForm.getEnd_time();
        this.repeat_yn = calendarDetailForm.getRepeat_yn();
        this.allday_yn = calendarDetailForm.getAllday_yn();
        this.calendar = calendar;
//        this.modr_id = calendarDetailForm.get
    }


    /**
     * 삭제 컬럼 변경
     */
    public void updateDelYn(String del_yn){
        this.del_yn = del_yn;
    }

}
