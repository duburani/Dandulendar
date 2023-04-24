package damdorani.dandulendar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class User extends HashMap<String, Object> {
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<UserGroup> userGroups = new ArrayList<>();

    protected User() {}
}
