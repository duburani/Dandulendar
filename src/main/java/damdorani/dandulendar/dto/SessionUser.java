package damdorani.dandulendar.dto;

import damdorani.dandulendar.domain.User;
import lombok.Getter;

@Getter
public class SessionUser {
    public SessionUser(User user) {
        this.user_id = user.getUser_id();
        this.user_name = user.getUser_name();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.provider = user.getProvider();
        this.couple_code = user.getCoupleCode();
        this.group_id = user.getGroup().getGroup_id();;
    }

    private String user_id;
    private String user_name;
    private String email;
    private String phone;
    private String provider;
    private String couple_code;
    private Long group_id;
}
