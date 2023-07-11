package damdorani.dandulendar.dto;

import damdorani.dandulendar.domain.Calendar;
import damdorani.dandulendar.domain.CalendarDetail;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CalendarDetailResponseDto {
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
    private Calendar calendar;

    public CalendarDetailResponseDto(CalendarDetail calendarDetail) {
        this.cal_dtl_id = calendarDetail.getCal_dtl_id();
        this.title = calendarDetail.getTitle();
        this.contents = calendarDetail.getContents();
        this.start_date = calendarDetail.getStart_date();
        this.start_time = calendarDetail.getStart_time();
        this.end_date = calendarDetail.getEnd_date();
        this.end_time = calendarDetail.getEnd_time();
        this.start_full_date = calendarDetail.getStart_full_date();
        this.end_full_date = calendarDetail.getEnd_full_date();
        this.repeat_yn = calendarDetail.getRepeat_yn();
        this.allday_yn = calendarDetail.getAllday_yn();
        this.regr_id = calendarDetail.getRegr_id();
        this.modr_id = calendarDetail.getModr_id();
        this.calendar = calendarDetail.getCalendar();
    }
}
