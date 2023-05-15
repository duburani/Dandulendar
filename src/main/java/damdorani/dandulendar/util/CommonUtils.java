package damdorani.dandulendar.util;

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
        String dateTime = date + " " + time;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dt = LocalDateTime.parse(dateTime, formatter);

        return dt;
    }
}
