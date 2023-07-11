package damdorani.dandulendar.controller;

import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.dto.GroupForm;
import damdorani.dandulendar.dto.SessionUser;
import damdorani.dandulendar.oauth2.LoginUser;
import damdorani.dandulendar.service.GroupService;
import damdorani.dandulendar.service.MainService;
import damdorani.dandulendar.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final GroupService groupService;
    private final MainService mainService;

    // 그룹 조회
    @GetMapping("/userGroup")
    public String userGroup(@RequestParam(required = false, name = "coupleCode") String coupleCode, Model model, @LoginUser SessionUser sessionUser){
        model.addAttribute("userInfo", sessionUser);
        model.addAttribute("coupleCode", coupleCode);

        String returnUrl = mainService.returnUrlByUserGroup(sessionUser);
        return "userGroup".equals(returnUrl) ? "user/" + returnUrl : "redirect:/" + returnUrl;
    }

    /*
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
     */

    // 그룹 생성
    @PostMapping("/userGroup")
    public String userGroup(@Valid GroupForm groupForm, Model model, @LoginUser SessionUser sessionUser){
        // 커플코드 확인
        User userByCoupleCode = userService.findUserByCoupleCode(groupForm.getCouple_code());
        if (userByCoupleCode == null)
            return "";
        /**
         * 여기 리턴 어케 시킬건데!!!!!!!
         *
         *
         *
         *
         *
         */

        // saveGroup
        model.addAttribute("groupForm", groupForm);
        Group group = groupService.saveGroup(groupForm);

        // User에 Group 저장
        userService.setGroup(sessionUser.getUser_id(), group);
        userService.setGroup(userByCoupleCode.getUser_id(), group);

        return "redirect:/calendars";
    }

    // 그룹 삭제


}
