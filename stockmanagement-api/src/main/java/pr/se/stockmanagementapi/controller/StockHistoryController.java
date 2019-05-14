package pr.se.stockmanagementapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pr.se.stockmanagementapi.model.StockHistory;
import pr.se.stockmanagementapi.respository.StockHistoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/stockhistory")
public class StockHistoryController {
    StockHistoryRepository stockHistoryRepository;

    public StockHistoryController(StockHistoryRepository stockHistoryRepository) {
        this.stockHistoryRepository = stockHistoryRepository;
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<List<StockHistory>> getStockHistoryByStockId(@PathVariable long stockId) {
        return ResponseEntity.ok(stockHistoryRepository.findByStockId(stockId));
    }
}
