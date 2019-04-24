package pr.se.stockmanagementapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pr.se.stockmanagementapi.model.Transaction;
import pr.se.stockmanagementapi.payload.StockTransactionRequest;
import pr.se.stockmanagementapi.respository.TransactionRepository;
import pr.se.stockmanagementapi.services.TransactionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionService transactionService, TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/purchases")
    public List<Transaction> getAllPurchases() {
        return transactionService.getAllPurchases();
    }

    @GetMapping("/sales")
    public List<Transaction> getAllSales() {
        return transactionService.getAllSales();
    }

    @GetMapping("/earnings/{depotId}")
    public double getEarnings(@PathVariable long depotId) {
        return transactionService.calculateEarnings(depotId);
    }

    @PostMapping("/purchase")
    public ResponseEntity purchaseStock(@Valid @RequestBody StockTransactionRequest stockTransactionRequest) {
        Transaction transaction = transactionService.purchase(stockTransactionRequest);
        return new ResponseEntity<>(transactionRepository.save(transaction), HttpStatus.CREATED);
    }

    @PostMapping("/sell")
    public ResponseEntity sellStock(@Valid @RequestBody StockTransactionRequest stockSellRequest) {
        Transaction transaction = transactionService.sell(stockSellRequest);
        return new ResponseEntity<>(transactionRepository.save(transaction), HttpStatus.CREATED);
    }
}
