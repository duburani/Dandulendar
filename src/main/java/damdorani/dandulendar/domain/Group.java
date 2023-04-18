package damdorani.dandulendar.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int group_id;

    private String group_name;
    private String memorial_date;
    private String drop_yn;

    @OneToMany(mappedBy = "group")
    private List<UserGroup> userGroups = new ArrayList<>();
}
