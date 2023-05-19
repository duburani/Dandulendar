package damdorani.dandulendar.dto;

import com.querydsl.core.annotations.QueryProjection;
import damdorani.dandulendar.domain.Calendar;
import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CalendarResponse {
    private int calId;
    private String calTitle;
    private String color;
    private String memorialYn;
    private int calCnt;
    private int groupId;

        @QueryProjection
        public CalendarResponse(Calendar calendar, int calCnt, int groupId){
            this.calId = calendar.getCal_id();
            this.calTitle = calendar.getCal_title();
            this.color = calendar.getColor();
            this.memorialYn = calendar.getMemorial_yn();
            this.calCnt = calCnt;
            this.groupId = groupId;
        }
}
