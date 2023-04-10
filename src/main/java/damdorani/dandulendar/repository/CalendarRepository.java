package damdorani.dandulendar.repository;

import damdorani.dandulendar.domain.Calendar;
import damdorani.dandulendar.domain.CalendarDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CalendarRepository {
    private final EntityManager em;

    public void saveCalendar(Calendar calendar){
        em.persist(calendar);
    }

    public Calendar findCalendar(int id){
        return em.find(Calendar.class, id);
    }

    public List<Calendar> findCalendarList(){
        return em.createQuery("select c from Calendar c where del_yn = 'N'", Calendar.class).getResultList();
    }



    public void saveCalendarDetail(CalendarDetail calendarDetail){
        em.persist(calendarDetail);
    }

    public CalendarDetail findCalendarDetail(int calDtlId) {
        return em.find(CalendarDetail.class, calDtlId);
    }

    public List<CalendarDetail> findCalendarDetailList() {
        return em.createQuery("select c from CalendarDetail c where del_yn = 'N'", CalendarDetail.class).getResultList();
    }
}
