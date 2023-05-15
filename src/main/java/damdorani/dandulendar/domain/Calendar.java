package damdorani.dandulendar.domain;

import damdorani.dandulendar.dto.CalendarForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "Calendar")
@Builder
@AllArgsConstructor
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cal_id;

    private String cal_title;
    private String color;
    private String memorial_yn;
    private String del_yn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public void updateCal(CalendarForm calendarForm){
        this.cal_title = calendarForm.getCal_title();
        this.color = calendarForm.getColor();
        this.memorial_yn = calendarForm.getMemorial_yn();
    }

    public void updateDelYn(String del_yn){
        this.del_yn = del_yn;
    }

    protected Calendar() {}

    @OneToMany(mappedBy = "calendar")
    private List<CalendarDetail> calendars = new ArrayList<>();
}
