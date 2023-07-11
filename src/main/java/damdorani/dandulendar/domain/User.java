package damdorani.dandulendar.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

import static org.springframework.data.jpa.domain.AbstractAuditable_.createdDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
public class User extends BaseTimeEntity implements Persistable<String> {
    @Id
    private String user_id;
    private String user_name;
    private String phone;
    private String email;
    private String provider;
    @Column(name = "couple_code")
    private String coupleCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Override
    public String getId() {
        return user_id;
    }

    @Override
    public boolean isNew() {
        return createdDate == null;
    }

    public void setGroup(Group group){
        this.group = group;
    }
}
