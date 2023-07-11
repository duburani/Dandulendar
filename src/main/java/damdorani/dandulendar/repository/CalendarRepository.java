package damdorani.dandulendar.repository;

import damdorani.dandulendar.domain.Calendar;
import damdorani.dandulendar.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findCalendarByGroup(Group group);
}
