package damdorani.dandulendar.config;

import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LoginUserAuditorAware implements AuditorAware<String> {
    private final HttpSession httpSession;

    @Override
    public Optional<String> getCurrentAuditor() {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user == null)
            return null;

        return Optional.ofNullable(user.getUser_id());
    }
}
