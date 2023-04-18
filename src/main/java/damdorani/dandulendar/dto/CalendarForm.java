package damdorani.dandulendar.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CalendarForm {
    private int cal_id;
    @NotEmpty(message = "달력 이름은 필수 입니다.")
    private String cal_title;
    @NotEmpty(message = "달력 색상은 필수 입니다.")
    private String color;
    private String memorial_yn;
    private int group_id;
}
