package damdorani.dandulendar.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import damdorani.dandulendar.domain.QUserGroup;
import damdorani.dandulendar.domain.UserGroup;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupForm {
    private int group_id;
    private String memorial_date;
    private String drop_yn;
    private String couple_code;
}
