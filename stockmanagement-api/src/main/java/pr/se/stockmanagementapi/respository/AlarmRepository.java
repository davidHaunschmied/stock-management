package pr.se.stockmanagementapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import pr.se.stockmanagementapi.model.Alarm;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.model.enums.AlarmType;

import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Optional<Alarm> findByStockAndAlarmType(Stock stock, AlarmType alarmType);
}
