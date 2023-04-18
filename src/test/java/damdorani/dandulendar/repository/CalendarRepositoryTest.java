package damdorani.dandulendar.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import damdorani.dandulendar.domain.Calendar;
import damdorani.dandulendar.domain.QCalendar;
import damdorani.dandulendar.domain.QCalendarDetail;
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
    EntityManager em;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Test
    public void queryDsl_findCalendarDetailList() throws Exception{
        // given
        QCalendar qCalendar = new QCalendar("title");
        QCalendarDetail qCalendarDetail = new QCalendarDetail("qCalendarDetail");

        List<Calendar> calendarList = jpaQueryFactory.selectFrom(qCalendar)
                .join(qCalendar.calendars, qCalendarDetail)
                .fetchJoin()
                .fetch();


        // when

        // then

    }
}