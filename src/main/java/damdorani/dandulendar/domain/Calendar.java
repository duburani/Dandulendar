package damdorani.dandulendar.domain;

import lombok.Getter;

import javax.persistence.*;

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
    private String group_id;

    public Calendar(String cal_title, String color, String memorial_yn, String group_id) {
        this.cal_title = cal_title;
        this.color = color;
        this.memorial_yn = memorial_yn;
        this.group_id = group_id;
    }

    protected Calendar() {}
}
