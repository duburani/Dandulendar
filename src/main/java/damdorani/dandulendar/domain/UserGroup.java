package damdorani.dandulendar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@DynamicInsert
public class UserGroup implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
//    @Column(name = "group_id")
    private Group group;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
//    @Column(name = "user_id")
    private User user;

    private LocalDateTime reg_dt;
    private String regr_id;
    private String modr_id;


    protected UserGroup() {

    }
}
