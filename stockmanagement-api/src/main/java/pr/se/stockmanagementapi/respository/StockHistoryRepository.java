package pr.se.stockmanagementapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pr.se.stockmanagementapi.model.StockHistory;

import java.util.List;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, StockHistory.StockHistoryId> {
    List<StockHistory> findByStockId(long stockId); // is this magic?
}
