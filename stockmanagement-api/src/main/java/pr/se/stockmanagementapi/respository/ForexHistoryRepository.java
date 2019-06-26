package pr.se.stockmanagementapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pr.se.stockmanagementapi.model.ForexHistory;

import java.util.List;

@Repository
public interface ForexHistoryRepository extends JpaRepository<ForexHistory, ForexHistory.ForexHistoryId> {
    List<ForexHistory> findByBaseAndConvertTo(String base, String convertTo);
}
