package damdorani.dandulendar.domain;

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
    private String cal_id;


    protected CalendarDetail() {

    }
}
