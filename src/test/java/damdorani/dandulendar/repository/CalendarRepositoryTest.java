package damdorani.dandulendar.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import damdorani.dandulendar.domain.Calendar;
import damdorani.dandulendar.domain.QCalendar;
import damdorani.dandulendar.domain.QCalendarDetail;
import damdorani.dandulendar.dto.CalendarResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CalendarRepositoryTest {
    @Autowired
    CalendarRepository calendarRepository;

    @Test
    public void findCalendarList() throws  Exception{
        List<CalendarResponse> calendarList = calendarRepository.findCalendarList(1);
        calendarList.size();
    }
}