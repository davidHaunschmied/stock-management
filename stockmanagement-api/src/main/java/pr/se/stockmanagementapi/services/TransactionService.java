package pr.se.stockmanagementapi.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.Holding;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.model.Transaction;
import pr.se.stockmanagementapi.model.enums.Currency;
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

    private final TransactionRepository transactionRepository;
    private final DepotService depotService;
    private final StockService stockService;
    private final ForexHistoryService forexHistoryService;

    public TransactionService(HoldingRepository holdingRepository, TransactionRepository transactionRepository, DepotService depotService, StockService stockService, ForexHistoryService forexHistoryService) {
        this.holdingRepository = holdingRepository;
        this.transactionRepository = transactionRepository;
        this.depotService = depotService;
        this.stockService = stockService;
        this.forexHistoryService = forexHistoryService;
    }

    @Transactional
    public Holding newTransaction(StockTransactionRequest stockTransactionRequest, TransactionType transactionType) {
        final Depot depot = depotService.findDepotByIdOrThrow(stockTransactionRequest.getDepotId());
        final Stock stock = stockService.findStockByIdOrThrow(stockTransactionRequest.getStockId());
        double price = stockTransactionRequest.getPrice();
        if (!stockTransactionRequest.getCurrency().getSymbol().equals(Currency.BASE_CURRENCY.getSymbol())) {
            price /= forexHistoryService.getCurrentExchangeRate(Currency.BASE_CURRENCY.getSymbol(), stockTransactionRequest.getCurrency().getSymbol());
        }
        Transaction transaction = new Transaction(stockTransactionRequest.getAmount(), price,
            new Date(), transactionType);
        Holding holding = holdingRepository.findByDepotAndStock(depot, stock).orElse(new Holding(depot, stock));
        holding.addTransaction(transaction);
        return holdingRepository.save(holding);
    }

    public List<Transaction> getAllByDepotId(long depotId, String currency) {
        List<Transaction> transactions = transactionRepository.findAll().stream().filter(t -> t.getHolding().getDepot().getId() == depotId).collect(Collectors.toList());
        if (!currency.equals(Currency.BASE_CURRENCY.getSymbol())) {
            double exchangeRate = forexHistoryService.getCurrentExchangeRate(Currency.BASE_CURRENCY.getSymbol(), currency);
            transactions.forEach(t -> t.setPrice(t.getPrice() * exchangeRate));
        }
        return transactions;

    }
}
