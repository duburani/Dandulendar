package damdorani.dandulendar.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "Calendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cal_id;

    private String cal_title;
    private String color;
    private String memorial_yn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Calendar(String cal_title, String color, String memorial_yn, Group group) {
        this.cal_title = cal_title;
        this.color = color;
        this.memorial_yn = memorial_yn;
        this.group = group;
    }

    protected Calendar() {}

    @OneToMany(mappedBy = "calendar")
    private List<CalendarDetail> calendars = new ArrayList<>();
}
