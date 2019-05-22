package pr.se.stockmanagementapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pr.se.stockmanagementapi.model.StockHistory;
import pr.se.stockmanagementapi.services.StockHistoryService;

import java.util.List;

@RestController
@RequestMapping("/api/stockhistory")
public class StockHistoryController {
    private final StockHistoryService stockHistoryService;

    public StockHistoryController(StockHistoryService stockHistoryService) {
        this.stockHistoryService = stockHistoryService;
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<List<StockHistory>> getStockHistoryByStockId(@PathVariable long stockId) {
        return ResponseEntity.ok(stockHistoryService.findByStockIdSorted(stockId));
    }
}
