package damdorani.dandulendar.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {
    @Id
    private String user_id;
    private String password;
    private String user_name;
    private String nickname;
    private String phone;
    private String email;
    private String reg_dt;
    private String mod_dt;
    private String provider;
    private String memorialDate;

    @OneToMany(mappedBy = "user")
    private List<UserGroup> userGroups = new ArrayList<>();


}
