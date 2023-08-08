package damdorani.dandulendar.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Mail {
    private String to;
    private String subject;
    private String message;
}