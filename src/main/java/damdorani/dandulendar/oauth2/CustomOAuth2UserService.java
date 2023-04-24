package damdorani.dandulendar.oauth2;

import damdorani.dandulendar.domain.Role;
import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.dto.SessionUser;
import damdorani.dandulendar.dto.UserForm;
import damdorani.dandulendar.repository.UserRepository;
import damdorani.dandulendar.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest); // Oath2 정보를 가져옴

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 소셜 정보 가져옴
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        String userId = registrationId + "_" + attributes.getId();
        String access_token = userRequest.getAccessToken().getTokenValue();

        User user;
        Optional<User> optionalUser = userRepository.findByUserId(userId);

        if (optionalUser.isPresent()){ // 로그인
            user = optionalUser.get();
        }else{ // 회원가입
            user = User.builder()
                    .user_id(userId)
                    .user_name(attributes.getUser_name())
                    .email(attributes.getEmail())
                    .phone(attributes.getPhone())
                    .provider(registrationId)
                    .build();
            userRepository.saveUser(user);
        }

        httpSession.setAttribute("user", new SessionUser(user));
        httpSession.setAttribute("access_token", access_token);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(Role.USER.getKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

}
