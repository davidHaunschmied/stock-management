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
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final HoldingRepository holdingRepository;
    private final SettingsService settingsService;
    private final TransactionRepository transactionRepository;
    private final DepotService depotService;
    private final StockService stockService;

    public TransactionService(HoldingRepository holdingRepository, SettingsService settingsService, TransactionRepository transactionRepository, DepotService depotService, StockService stockService) {
        this.holdingRepository = holdingRepository;
        this.settingsService = settingsService;
        this.transactionRepository = transactionRepository;
        this.depotService = depotService;
        this.stockService = stockService;
    }

    @Transactional
    public Holding newTransaction(StockTransactionRequest stockTransactionRequest, TransactionType transactionType) {
        final Depot depot = depotService.findDepotByIdOrThrow(stockTransactionRequest.getDepotId());
        final Stock stock = stockService.findStockByIdOrThrow(stockTransactionRequest.getStockId());
        Transaction transaction = new Transaction(stockTransactionRequest.getAmount(),
            getPriceWithCharges(transactionType, stockTransactionRequest.getAmount() * stock.getPrice()),
            new Date(), transactionType);
        Holding holding = holdingRepository.findByDepotAndStock(depot, stock).orElse(new Holding(depot, stock));
        holding.addTransaction(transaction);
        return holdingRepository.save(holding);
    }

    public List<Transaction> getAllByDepotId(long depotId) {
        return transactionRepository.findAll().stream().filter(t -> t.getHolding().getDepot().getId() == depotId).collect(Collectors.toList());
    }

    private double getPriceWithCharges(TransactionType transactionType, double price) {
        Settings settings = settingsService.getSettings();
        if (transactionType == TransactionType.SALE) {
            return price - calculateCharges(settings.getFlatSellCharges(), settings.getRelativeSellCharges(), price);
        } else if (transactionType == TransactionType.PURCHASE) {
            return price + calculateCharges(settings.getFlatPurchaseCharges(), settings.getRelativePurchaseCharges(), price);
        }
        return 0;
    }

    private double calculateCharges(double minFlatCharges, double relativeChargesPercent, double price) {
        double relativeCharge = relativeChargesPercent / 100 * price;
        return relativeCharge < minFlatCharges ? minFlatCharges : relativeCharge;
    }
}
