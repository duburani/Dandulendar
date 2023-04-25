package damdorani.dandulendar.security;

import damdorani.dandulendar.jwt.JwtAuthenticationFilter;
import damdorani.dandulendar.jwt.JwtTokenProvider;
import damdorani.dandulendar.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated().and()

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)

                .oauth2Login()
                .defaultSuccessUrl("/userGroup")

                .userInfoEndpoint()
                .userService(customOAuth2UserService)
        ;
        // 여기서부터 로그아웃 API 내용~!
        http.logout()
                .logoutUrl("/logout")   // 로그아웃 처리 URL (= form action url)
                //.logoutSuccessUrl("/login") // 로그아웃 성공 후 targetUrl,
                // logoutSuccessHandler 가 있다면 효과 없으므로 주석처리.
                .addLogoutHandler((request, response, authentication) -> {
                    // 사실 굳이 내가 세션 무효화하지 않아도 됨.
                    // LogoutFilter가 내부적으로 해줌.
                    HttpSession session = request.getSession();
                    if (session != null) {
                        session.invalidate();
                    }
                })  // 로그아웃 핸들러 추가
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.sendRedirect("/");
                }); // 로그아웃 성공 핸들러
//                .deleteCookies("remember-me"); // 로그아웃 후 삭제할 쿠키 지정
        return http.build();
    }

    /**
     * .headers().frameOptions().disable() = h2-console 화면을 사용하기 위해 해당옵션들을 disable합니다.
     * .authorizeRequests() = URL별 권한 관리를 설정하는 옵션의 시작점입니다. 이후 antMatchers() 옵션을 사용가능합니다.
     * .antMatchers() = 권한 관리 대상을 지정하는 옵션입니다.URL,HTTP 메소드별로 관리가 가능합니다.
     * .anyRequest().authenticated() = 설정값 이외의 url에 대한 설정입니다. 이외의 url은 모두 인증된 사용자에게만 허용합니다.
     * .and().logout().logoutSuccessUrl("/") = 로그아웃 성공시 "/"로 이동
     * .oauth2Login() = oauth2 로그인 설정의 시작점
     * .userInfoEndpoint() = oauth2 로그인 성공 후 사용자정보를 가져올때 설정담당
     * .userService() = 소셜로그인성공후 후속조치를 진행할 userService 인터페이스의 구현제등록
     * 소셜서비스에서 사용자정보를 가져온 상태에서 추가로 진행하고자하는 기능을 명시할 수 있음
     */
}
