package damdorani.dandulendar.controller;

import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.domain.UserGroup;
import damdorani.dandulendar.dto.GroupForm;
import damdorani.dandulendar.dto.UserForm;
import damdorani.dandulendar.dto.UserGroupForm;
import damdorani.dandulendar.service.GroupService;
import damdorani.dandulendar.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final GroupService groupService;

    // 그룹 조회
    @GetMapping("/userGroup")
    public String userGroup(@RequestParam(required = false, name = "coupleCode") String coupleCode, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();

        Object objUser = session.getAttribute("user");
        if(objUser == null) {
            return "redirect:/";
        }

        model.addAttribute("userInfo", objUser);
        model.addAttribute("coupleCode", coupleCode);
        return "user/userGroup";
    }

    // 그룹 생성
    @PostMapping("/userGroup")
    public String userGroup(@Valid GroupForm groupForm, UserGroupForm userGroupForm, Model model){

        // saveGroup
        groupForm.setMemorial_date(groupForm.getMemorial_date().replace("-",""));
        model.addAttribute("groupForm", groupForm);
        int groupId = groupService.saveGroup(groupForm);

        // saveUserGroup
        groupService.saveUserGroup(userGroupForm, groupId);

        return "redirect:/calendars";
    }

    // 그룹 삭제


}
