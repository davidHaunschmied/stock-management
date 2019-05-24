package pr.se.stockmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.model.StockHistory;
import pr.se.stockmanagementapi.respository.StockRepository;
import pr.se.stockmanagementapi.services.StockHistoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockHistoryService stockHistoryService;
    private final StockRepository stockRepository;

    @Autowired
    public StockController(StockHistoryService stockHistoryService, StockRepository stockRepository) {
        this.stockHistoryService = stockHistoryService;
        this.stockRepository = stockRepository;
    }

    @GetMapping("/all")
    public List<Stock> getAllStocks() { return stockRepository.findAll();}

    @GetMapping("/detail/{stockId}")
    public ResponseEntity<Optional<Stock>> getStock(@PathVariable long stockId) {
        return ResponseEntity.ok(stockRepository.findById(stockId));
    }

    @GetMapping("/history/{stockId}")
    public ResponseEntity<List<StockHistory>> getStockHistoryByStockId(@PathVariable long stockId) {
        return ResponseEntity.ok(stockHistoryService.findByStockIdSorted(stockId));
    }
}
