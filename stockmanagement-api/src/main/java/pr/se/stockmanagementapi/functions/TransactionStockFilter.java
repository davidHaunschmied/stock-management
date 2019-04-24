package pr.se.stockmanagementapi.functions;

import pr.se.stockmanagementapi.model.Transaction;

import java.util.function.Predicate;

public class TransactionStockFilter implements Predicate<Transaction> {

    private final Long stockId;

    public TransactionStockFilter(Long depotId) {
        this.stockId = depotId;
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction.getStock().getId().equals(stockId);
    }
}
