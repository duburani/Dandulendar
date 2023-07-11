package damdorani.dandulendar.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import damdorani.dandulendar.domain.Calendar;
import damdorani.dandulendar.domain.CalendarDetail;
import damdorani.dandulendar.dto.CalendarRequestDto;
import damdorani.dandulendar.dto.CalendarResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static damdorani.dandulendar.domain.QCalendar.calendar;
import static damdorani.dandulendar.domain.QCalendarDetail.calendarDetail;

@Repository
@RequiredArgsConstructor
public class CalendarJpaRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public void saveCalendar(Calendar calendar){
        em.persist(calendar);
    }

    public Calendar findCalendar(Long id){
        return em.find(Calendar.class, id);
    }

    public List<CalendarResponseDto> findCalendarList(Long groupId){
        return queryFactory
                .select(Projections.constructor(CalendarResponseDto.class, calendar, calendar.calendars.size(), calendar.group.group_id))
                .from(calendar)
                .where(calendar.del_yn.eq("N")
//                        .and(calendar.group.group_id.eq(groupId))
                )
                .fetch();
    }

    public void saveCalendarDetail(CalendarDetail calendarDetail){
        em.persist(calendarDetail);
    }

    public CalendarDetail findCalendarDetail(Long calDtlId) {
        return em.find(CalendarDetail.class, calDtlId);
    }

    public List<Calendar> findCalendarDetailList(CalendarRequestDto calendarRequestDto) {
        return queryFactory
                .selectDistinct(calendar)
                .from(calendar)
                .join(calendar.calendars, calendarDetail)
                .where(
                        calendarDetail.del_yn.eq("N")
                                .and(calendar.group.group_id.eq(calendarRequestDto.getGroupId()))
                        .and(calendarDetail.start_full_date.between(calendarRequestDto.getStartStr(), calendarRequestDto.getEndStr()))
                        .and(calendarDetail.end_full_date.between(calendarRequestDto.getStartStr(), calendarRequestDto.getEndStr()))
                )
                .fetchJoin()
                .fetch();
    }
}
