package pr.se.stockmanagementapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.Holding;
import pr.se.stockmanagementapi.model.Stock;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {
    Optional<Holding> findByDepotAndStock(Depot depot, Stock stock);

    List<Holding> findByDepotId(long depotId);

    Optional<Holding> findByDepotIdAndStockId(Long depotId, Long stockId);

    List<Holding> findByDepot(Depot depot);

    List<Holding> findAllByStockId(Long stockId);
}
