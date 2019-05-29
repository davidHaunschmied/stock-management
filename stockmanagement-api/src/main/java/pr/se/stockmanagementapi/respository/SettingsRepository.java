package pr.se.stockmanagementapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import pr.se.stockmanagementapi.model.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
}
