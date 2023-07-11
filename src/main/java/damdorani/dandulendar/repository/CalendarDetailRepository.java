package damdorani.dandulendar.repository;

import damdorani.dandulendar.domain.CalendarDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarDetailRepository extends JpaRepository<CalendarDetail, Long> {
}
