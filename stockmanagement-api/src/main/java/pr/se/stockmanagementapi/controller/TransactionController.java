package pr.se.stockmanagementapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pr.se.stockmanagementapi.model.Holding;
import pr.se.stockmanagementapi.model.Transaction;
import pr.se.stockmanagementapi.model.enums.TransactionType;
import pr.se.stockmanagementapi.payload.StockTransactionRequest;
import pr.se.stockmanagementapi.services.TransactionService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/purchases")
    public List<Transaction> getAllPurchases() {
        return transactionService.getAllPurchases();
    }

    @GetMapping("/sales")
    public List<Transaction> getAllSales() {
        return transactionService.getAllSales();
    }

    @PostMapping("/purchase")
    public ResponseEntity<Holding> purchaseStock(@Valid @RequestBody StockTransactionRequest stockTransactionRequest) {
        return new ResponseEntity<>(transactionService.newTransaction(stockTransactionRequest, TransactionType.PURCHASE), HttpStatus.CREATED);
    }

    @PostMapping("/sell")
    public ResponseEntity<Holding> sellStock(@Valid @RequestBody StockTransactionRequest stockTransactionRequest) {
        return new ResponseEntity<>(transactionService.newTransaction(stockTransactionRequest, TransactionType.SALE), HttpStatus.CREATED);
    }
}
