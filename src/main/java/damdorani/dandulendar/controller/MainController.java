package damdorani.dandulendar.controller;

import damdorani.dandulendar.dto.UserGroupResponse;
import damdorani.dandulendar.dto.SessionUser;
import damdorani.dandulendar.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final HttpSession session;
    private final GroupService groupService;

    @GetMapping("/")
    public String login(Model model) {
        String returnUrl = "main";

        Object objUser = session.getAttribute("user");
        if(objUser != null){
            SessionUser sessionUser = (SessionUser) objUser;
            String userId = sessionUser.getUser_id();
            List<UserGroupResponse> groupList = groupService.findGroupByUserId(userId);

            if(groupList.size() < 1){
                returnUrl = "redirect:/userGroup";
            }else{
                returnUrl = "redirect:/calendars";
            }

            model.addAttribute("userInfo", sessionUser);
            model.addAttribute("groupInfo", groupList);
        }

        return returnUrl;
    }
}