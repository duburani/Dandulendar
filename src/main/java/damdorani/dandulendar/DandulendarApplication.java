package damdorani.dandulendar;

import damdorani.dandulendar.dto.SessionUser;
import damdorani.dandulendar.oauth2.LoginUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@SpringBootApplication
public class DandulendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(DandulendarApplication.class, args);
	}
}
