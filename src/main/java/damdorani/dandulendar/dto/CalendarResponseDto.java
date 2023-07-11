package damdorani.dandulendar.dto;

import com.querydsl.core.annotations.QueryProjection;
import damdorani.dandulendar.domain.Calendar;
import damdorani.dandulendar.domain.CalendarDetail;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CalendarResponseDto {
    private Long calId;
    private String calTitle;
    private String color;
    private String memorialYn;
    private Long calCnt;
    private Long groupId;
    private List<CalendarDetail> calendarDetailList = new ArrayList<>();

    @QueryProjection
    public CalendarResponseDto(Calendar calendar, Long calCnt, Long groupId){
        this.calId = calendar.getCal_id();
        this.calTitle = calendar.getCal_title();
        this.color = calendar.getColor();
        this.memorialYn = calendar.getMemorial_yn();
        this.calCnt = calCnt;
        this.groupId = groupId;
    }

    @Builder
    public CalendarResponseDto(Calendar calendar) {
        this.calId = calendar.getCal_id();
        this.calTitle = calendar.getCal_title();
        this.color = calendar.getColor();
        this.memorialYn = calendar.getMemorial_yn();
        this.groupId = calendar.getGroup().getGroup_id();
        this.calendarDetailList = calendar.getCalendars();
    }
}
