package damdorani.dandulendar.controller;

import damdorani.dandulendar.domain.UserGroup;
import damdorani.dandulendar.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

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
    @PutMapping("/userGroup")
    public String userGroup(@Valid UserGroup userGroup){
        return "";
    }

    // 그룹 삭제


}
