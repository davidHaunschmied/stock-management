package pr.se.stockmanagementapi.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.Holding;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.model.Transaction;
import pr.se.stockmanagementapi.model.enums.TransactionType;
import pr.se.stockmanagementapi.payload.StockTransactionRequest;
import pr.se.stockmanagementapi.respository.HoldingRepository;
import pr.se.stockmanagementapi.respository.TransactionRepository;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    private final HoldingRepository holdingRepository;

    private final TransactionRepository transactionRepository;
    private final DepotService depotService;
    private final StockService stockService;

    public TransactionService(HoldingRepository holdingRepository, TransactionRepository transactionRepository, DepotService depotService, StockService stockService) {
        this.holdingRepository = holdingRepository;
        this.transactionRepository = transactionRepository;
        this.depotService = depotService;
        this.stockService = stockService;
    }

    public List<Transaction> getAllPurchases() {
        return transactionRepository.findAllByTransactionType(TransactionType.PURCHASE);
    }

    public List<Transaction> getAllSales() {
        return transactionRepository.findAllByTransactionType(TransactionType.SALE);
    }

    @Transactional
    public Holding newTransaction(StockTransactionRequest stockTransactionRequest, TransactionType transactionType) {
        final Depot depot = depotService.findDepotByIdOrThrow(stockTransactionRequest.getDepotId());
        final Stock stock = stockService.findStockByIdOrThrow(stockTransactionRequest.getStockId());
        Transaction transaction = new Transaction(stockTransactionRequest.getAmount(), stockTransactionRequest.getPrice(),
            new Date(), transactionType);
        Holding holding = holdingRepository.findByDepotAndStock(depot, stock).orElse(new Holding(depot, stock));
        holding.addTransaction(transaction);
        return holdingRepository.save(holding);
    }
}
