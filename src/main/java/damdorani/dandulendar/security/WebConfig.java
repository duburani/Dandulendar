package damdorani.dandulendar.security;

import damdorani.dandulendar.oauth2.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // HandlerMethodArgumentResolver는 항상 WebMvcConfigure의 addArgumentResolvers()를 통해 추가해야 함.
        // 다른 Handler-MethodArgumentResolver가 필요할 때 같은 방식으로 추가해주면 됨.
        argumentResolvers.add(loginUserArgumentResolver);
    }
}