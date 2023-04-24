package damdorani.dandulendar.controller;

import damdorani.dandulendar.domain.Role;
import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.dto.UserForm;
import damdorani.dandulendar.jwt.JwtTokenProvider;
import damdorani.dandulendar.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/")
    public String login(Model model) {
        return "main";
    }

    @GetMapping("/join")
    public String join(Authentication authentication, Model model, RedirectAttributes redirectAttributes){
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> responseMap = (Map<String, Object>) oAuth2User.getAttribute("response");

        String provider = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        String userId = provider + "_" + String.valueOf(responseMap.get("id"));

        User user = userService.findUserById(userId);

        String returnUrl = "";

        if(user == null)  // 회원가입
        {
            UserForm userForm = new UserForm();
            userForm.setUser_id(userId);
            userForm.setEmail(String.valueOf(responseMap.get("email")));
            userForm.setPhone(String.valueOf(responseMap.get("mobile")));
            userForm.setUser_name(String.valueOf(responseMap.get("name")));
            userForm.setProvider(provider);

            model.addAttribute("userInfo", userForm);
            returnUrl = "user/join";
        }else{ // 로그인
            String token = jwtTokenProvider.generateToken(userId, Role.USER.getKey());
            redirectAttributes.addAttribute("token", token);

            returnUrl = "redirect:/calendars";
        }

        return returnUrl;
    }
}