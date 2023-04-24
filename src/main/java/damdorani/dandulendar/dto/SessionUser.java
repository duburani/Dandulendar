package damdorani.dandulendar.dto;

import damdorani.dandulendar.domain.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    SessionUser() {
    }

    public SessionUser(User user) {
        this.user_id = user.getUser_id();
        this.user_name = user.getUser_name();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.provider = user.getProvider();
    }

    private String user_id;
    private String user_name;
    private String email;
    private String phone;
    private String provider;

}
