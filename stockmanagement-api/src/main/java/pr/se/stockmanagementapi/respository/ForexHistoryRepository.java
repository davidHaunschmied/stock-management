package pr.se.stockmanagementapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pr.se.stockmanagementapi.model.ForexHistory;

@Repository
public interface ForexHistoryRepository extends JpaRepository<ForexHistory, ForexHistory.ForexHistoryId> {
}
