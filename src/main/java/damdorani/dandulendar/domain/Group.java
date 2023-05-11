package damdorani.dandulendar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@DynamicInsert
@Table(name = "Groupz")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int group_id;
    private String memorial_date;
    private String drop_yn;

    @OneToMany(mappedBy = "group")
    private List<UserGroup> userGroups = new ArrayList<>();

    protected Group() {

    }
}
