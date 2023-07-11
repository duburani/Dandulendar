package damdorani.dandulendar.domain;

import damdorani.dandulendar.dto.CalendarDetailForm;
import damdorani.dandulendar.util.CommonUtils;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Table(name="CalendarDetail")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
public class CalendarDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cal_dtl_id;
    private String title;
    private String contents;
    private String start_date;
    private String start_time;
    private String end_date;
    private String end_time;
    private LocalDateTime start_full_date;
    private LocalDateTime end_full_date;
    private String repeat_yn;
    private String allday_yn;
    private String regr_id;
    private String modr_id;
    private String del_yn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cal_id")
    private Calendar calendar;


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
        this.start_full_date = CommonUtils.convertTime(calendarDetailForm.getStart_date(), calendarDetailForm.getEnd_time());
        this.end_full_date = CommonUtils.convertTime(calendarDetailForm.getEnd_date(), calendarDetailForm.getEnd_time());
        this.repeat_yn = calendarDetailForm.getRepeat_yn();
        this.allday_yn = calendarDetailForm.getAllday_yn();
        this.calendar = calendar;
    }

    /**
     * 삭제 컬럼 변경
     */
    public void updateDelYn(String del_yn){
        this.del_yn = del_yn;
    }

}
