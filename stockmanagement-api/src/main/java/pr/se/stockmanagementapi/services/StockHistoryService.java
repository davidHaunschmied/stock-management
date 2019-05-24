package pr.se.stockmanagementapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockmanagementapi.model.StockHistory;
import pr.se.stockmanagementapi.respository.StockHistoryRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StockHistoryService {
    private final StockHistoryRepository stockHistoryRepository;

    @Autowired
    public StockHistoryService(StockHistoryRepository stockHistoryRepository) {
        this.stockHistoryRepository = stockHistoryRepository;
    }

    public List<StockHistory> findByStockIdSorted(long stockId) {
        return stockHistoryRepository.findByStockId(stockId).stream().sorted(Comparator.comparing(StockHistory::getDateMillis)).collect(Collectors.toList());
    }

    public Optional<StockHistory> findByStockIdAndTime(long stockId, long timeInMillis) {
        return stockHistoryRepository.findByStockId(stockId).stream().filter(stockHistory -> stockHistory.getDateMillis() <= timeInMillis).max(Comparator.comparing(StockHistory::getDateMillis));
    }
}
