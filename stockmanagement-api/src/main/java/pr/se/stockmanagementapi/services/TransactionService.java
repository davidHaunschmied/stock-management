package pr.se.stockmanagementapi.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pr.se.stockmanagementapi.model.*;
import pr.se.stockmanagementapi.model.enums.TransactionType;
import pr.se.stockmanagementapi.payload.StockTransactionRequest;
import pr.se.stockmanagementapi.respository.HoldingRepository;
import pr.se.stockmanagementapi.respository.TransactionRepository;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    private final HoldingRepository holdingRepository;
    private final Settings settings;
    private final TransactionRepository transactionRepository;
    private final DepotService depotService;
    private final StockService stockService;

    public TransactionService(HoldingRepository holdingRepository, SettingsService settingsService, TransactionRepository transactionRepository, DepotService depotService, StockService stockService) {
        this.holdingRepository = holdingRepository;
        this.settings = settingsService.getSettings();
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
        Transaction transaction = new Transaction(stockTransactionRequest.getAmount(),
            stockTransactionRequest.getPrice() + getCharges(transactionType, stockTransactionRequest.getPrice()),
            new Date(), transactionType);
        Holding holding = holdingRepository.findByDepotAndStock(depot, stock).orElse(new Holding(depot, stock));
        holding.addTransaction(transaction);
        return holdingRepository.save(holding);
    }

    private double getCharges(TransactionType transactionType, double price) {
        if (transactionType == TransactionType.SALE) {
            return settings.getFlatSellCharges() + settings.getRelativeSellCharges() * price;
        } else if (transactionType == TransactionType.PURCHASE) {
            return settings.getFlatPurchaseCharges() + settings.getRelativePurchaseCharges() * price;
        }
        return 0;
    }
}
