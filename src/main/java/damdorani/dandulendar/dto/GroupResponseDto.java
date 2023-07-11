package damdorani.dandulendar.dto;

import damdorani.dandulendar.domain.Group;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupResponseDto {
    private Long group_id;
    private String memorial_date;

    public GroupResponseDto(Group group) {
        this.group_id = group.getGroup_id();
        this.memorial_date = group.getMemorial_date();
    }
}
