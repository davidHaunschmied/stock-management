package pr.se.stockmanagementapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import pr.se.stockmanagementapi.model.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
