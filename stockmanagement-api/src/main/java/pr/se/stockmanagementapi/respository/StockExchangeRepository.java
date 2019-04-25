package pr.se.stockmanagementapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pr.se.stockmanagementapi.model.StockExchange;

import java.util.Optional;

@Repository
public interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {
    Optional<StockExchange> findByShortName(String shortName);
}
