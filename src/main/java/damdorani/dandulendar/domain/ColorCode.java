package damdorani.dandulendar.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ColorCode {

    PRIMARY("primary", "green")
    ,LIGHT("light", "light")
    ,DANGER("danger", "red")
    ,WARNING("warning", "yellow")
    ,INFO("info", "blue")
    ,DARK("dark", "dark-gray")
    ;

    private final String code_id;
    private final String code_name;
}
