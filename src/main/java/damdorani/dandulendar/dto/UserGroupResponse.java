package damdorani.dandulendar.dto;

import com.querydsl.core.annotations.QueryProjection;
import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserGroupResponse {
    private int group_id;
    private String memorial_date;
    private String user_id;
    private String user_name;

    @QueryProjection
    public UserGroupResponse(Group group, User user){
        this.group_id = group.getGroup_id();
        this.memorial_date = group.getMemorial_date();
        this.user_id = user.getUser_id();
        this.user_name = user.getUser_name();
    }
}
