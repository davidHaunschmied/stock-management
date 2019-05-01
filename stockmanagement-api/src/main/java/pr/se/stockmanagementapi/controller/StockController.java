package pr.se.stockmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.respository.StockRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockRepository stockRepository;

    @Autowired
    public StockController(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @GetMapping("/all")
    public List<Stock> getAllStocks() { return stockRepository.findAll();}

    @GetMapping("/stock-detail/{stockId}")
    public ResponseEntity<Optional<Stock>> getStock(@PathVariable long stockId) {
        return ResponseEntity.ok(stockRepository.findById(stockId));
    }

}
