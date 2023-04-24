package damdorani.dandulendar.oauth2;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String id;
    private String user_name;
    private String email;
    private String phone;

    public static OAuthAttributes of(String socialName, String userNameAttributeName, Map<String, Object> attributes){
        if("kakao".equals(socialName)){
            return ofKakao(userNameAttributeName, attributes);
        }else if("naver".equals(socialName)) {
            return ofNaver(userNameAttributeName, attributes);
        }

        return null;
    }

    /**
     * 네이버 로그인
     * @param userNameAttributeName
     * @param attributes
     * @return
     */
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        return OAuthAttributes.builder()
                .id(String.valueOf(response.get("id")))
                .user_name(String.valueOf(response.get("name")))
                .email(String.valueOf(response.get("email")))
                .phone(String.valueOf(response.get("mobile")))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /**
     * 카카오 로그인
     * @param userNameAttributeName
     * @param attributes
     * @return
     */
    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .user_name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .build();
    }

}
