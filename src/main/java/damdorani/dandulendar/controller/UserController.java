package damdorani.dandulendar.controller;

import damdorani.dandulendar.domain.UserGroup;
import damdorani.dandulendar.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    // 회원 로그아웃

    // 그룹 조회
    @GetMapping("/userGroup")
    public String userGroup(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        model.addAttribute("userInfo", session.getAttribute("user"));
        return "user/userGroup";
    }

    // 그룹 생성
    @PutMapping("/userGroup")
    public String userGroup(@Valid UserGroup userGroup){



        return "";
    }

    // 그룹 삭제


}
