package damdorani.dandulendar.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CalendarDetailForm {
    private int cal_dtl_id;
    @NotEmpty(message = "일정명은 필수 입니다.")
    private String title;
    private String contents;
    private String start_date;
    private String start_time;
    private String end_date;
    private String end_time;
    private String repeat_yn;
    private String allday_yn;
    private int cal_id;
}
