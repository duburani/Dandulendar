package damdorani.dandulendar.controller;

import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.dto.GroupForm;
import damdorani.dandulendar.dto.UserGroupResponse;
import damdorani.dandulendar.dto.SessionUser;
import damdorani.dandulendar.service.GroupService;
import damdorani.dandulendar.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final GroupService groupService;
    private final HttpSession session;

    // 그룹 조회
    @GetMapping("/userGroup")
    public String userGroup(@RequestParam(required = false, name = "coupleCode") String coupleCode, Model model){
        Object objUser = session.getAttribute("user");
        if(objUser != null){
            SessionUser sessionUser = (SessionUser) objUser;
            String userId = sessionUser.getUser_id();
            List<UserGroupResponse> groupList = groupService.findGroupByUserId(userId);

            if(groupList.size() > 1){
                model.addAttribute("groupInfo", groupList);
                return "redirect:/calendars";
            }

            model.addAttribute("userInfo", sessionUser);
        }else{
            return "redirect:/";
        }

        model.addAttribute("coupleCode", coupleCode);
        return "user/userGroup";
    }

    // 그룹 생성
    @PostMapping("/userGroup")
    public String userGroup(@Valid GroupForm groupForm, Model model){

        // saveGroup
        model.addAttribute("groupForm", groupForm);
        int groupId = groupService.saveGroup(groupForm);

        // saveUserGroup
        SessionUser objUser = (SessionUser) session.getAttribute("user");
        groupService.saveUserGroup(objUser.getUser_id(), groupId);

        // CC 찾기
        Optional<User> userByCode = userService.findUserByCoupleCode(groupForm.getCouple_code());
        groupService.saveUserGroup(userByCode.get().getUser_id(), groupId);


        return "redirect:/calendars";
    }

    // 그룹 삭제


}
