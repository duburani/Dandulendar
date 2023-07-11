package damdorani.dandulendar.service;

import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.dto.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    @Autowired
    private UserService userService;

    public String returnUrlByUserGroup(SessionUser sessionUser) {
        if (sessionUser == null)
            return "main";

        User user = userService.findUser(sessionUser.getUser_id());

        if (user == null)
            return "main";

        if (user.getGroup() == null) {
            return "userGroup";
        } else {
            return "calendars";
        }
    }
}
