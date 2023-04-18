package damdorani.dandulendar.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {

    private String user_id;
    private String password;
    private String user_name;
    private String nickname;
    private String phone;
    private String email;
    private String reg_dt;
    private String mod_dt;
    private String provider;
    private String code;

}
