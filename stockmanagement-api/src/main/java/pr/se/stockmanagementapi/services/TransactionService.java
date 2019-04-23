package pr.se.stockmanagementapi.services;

import org.springframework.stereotype.Service;
import pr.se.stockmanagementapi.exceptions.BadRequestException;
import pr.se.stockmanagementapi.functions.TransactionDepotFilter;
import pr.se.stockmanagementapi.functions.TransactionStockFilter;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.model.Transaction;
import pr.se.stockmanagementapi.model.enums.TransactionType;
import pr.se.stockmanagementapi.payload.StockPurchaseRequest;
import pr.se.stockmanagementapi.payload.StockSellRequest;
import pr.se.stockmanagementapi.respository.DepotRepository;
import pr.se.stockmanagementapi.respository.StockRepository;
import pr.se.stockmanagementapi.respository.TransactionRepository;

import java.util.List;
import java.util.stream.Stream;

@Service
public class TransactionService {

    private final StockRepository stockRepository;
    private final DepotRepository depotRepository;

    private final TransactionRepository transactionRepository;

    public TransactionService(StockRepository stockRepository, DepotRepository depotRepository, TransactionRepository transactionRepository) {
        this.stockRepository = stockRepository;
        this.depotRepository = depotRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllPurchases() {
        return transactionRepository.findAllByTransactionType(TransactionType.PURCHASE);
    }

    public List<Transaction> getAllSales() {
        return transactionRepository.findAllByTransactionType(TransactionType.SALE);
    }

    public double calculateEarnings(Long depotId) {
        return allDepotSales(depotId).mapToDouble(Transaction::getPrice).sum() - allDepotPurchases(depotId).mapToDouble(Transaction::getPrice).sum();
    }

    public Transaction purchase(StockPurchaseRequest stockPurchaseRequest) {
        return Transaction.purchase(stockPurchaseRequest.getAmount(), stockPurchaseRequest.getPrice(),
            stockPurchaseRequest.getDate(),
            findStockByIdOrThrow(stockPurchaseRequest.getStockId()),
            findDepotByIdOrThrow(stockPurchaseRequest.getDepotId()));
    }

    public Transaction sell(StockSellRequest stockSellRequest) {
        int currentStockAmount = getCurrentAmountFor(stockSellRequest.getDepotId(), stockSellRequest.getStockId());
        if (!(currentStockAmount >= stockSellRequest.getAmount())) {
            throw new BadRequestException("You cannot sell more stocks than you own!");
        }
        return Transaction.sell(stockSellRequest.getAmount(), stockSellRequest.getPrice(),
            stockSellRequest.getDate(),
            findStockByIdOrThrow(stockSellRequest.getStockId()),
            findDepotByIdOrThrow(stockSellRequest.getDepotId()));
    }

    private Stock findStockByIdOrThrow(long id) {
        return stockRepository.findById(id).orElseThrow(() -> new BadRequestException("Stock with id " + id + " not found!"));
    }

    private Depot findDepotByIdOrThrow(long id) {
        return depotRepository.findById(id).orElseThrow(() -> new BadRequestException("Depot with id " + id + " not found!"));
    }

    private int getCurrentAmountFor(Long depotId, Long stockId) {
        return allDepotPurchases(depotId).filter(byStockId(stockId)).mapToInt(Transaction::getAmount).sum()
            - allDepotSales(depotId).filter(byStockId(stockId)).mapToInt(Transaction::getAmount).sum();
    }

    private Stream<Transaction> allDepotPurchases(Long depotId) {
        return getAllPurchases().stream().filter(byDepotId(depotId));
    }

    private Stream<Transaction> allDepotSales(Long depotId) {
        return getAllSales().stream().filter(byDepotId(depotId));
    }

    private static TransactionDepotFilter byDepotId(Long depotId) {
        return new TransactionDepotFilter(depotId);
    }

    private static TransactionStockFilter byStockId(Long stockId) {
        return new TransactionStockFilter(stockId);
    }
}
