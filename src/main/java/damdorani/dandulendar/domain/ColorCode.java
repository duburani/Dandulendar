package damdorani.dandulendar.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ColorCode {
    PRIMARY("primary", "green", "#1abc9c")
    ,LIGHT("light", "light", "#f8f9fa")
    ,DANGER("danger", "red", "#dc3545")
    ,WARNING("warning", "yellow", "#ffc107")
    ,INFO("info", "blue", "#0dcaf0")
    ,DARK("dark", "dark-gray", "#212529")
    ;

    private final String code_id;
    private final String code_name;
    private final String rgb_color;
}
