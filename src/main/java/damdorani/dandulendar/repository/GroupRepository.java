package damdorani.dandulendar.repository;

import damdorani.dandulendar.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
//    Optional<Group> findGroupByUserId(@Param("userId") String userId);
}
