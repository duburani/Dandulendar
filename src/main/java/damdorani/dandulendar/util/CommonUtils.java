package damdorani.dandulendar.util;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtils {
    /**
     * String to LocalDateTime 타입 변환
     * @param date
     * @param time
     * @return
     */
    public static LocalDateTime convertTime(String date, String time){
        String dateTime = date + "T" + time;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime dt = LocalDateTime.parse(dateTime, formatter);

        return dt;
    }
}
