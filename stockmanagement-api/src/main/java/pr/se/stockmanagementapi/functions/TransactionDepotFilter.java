package pr.se.stockmanagementapi.functions;

import pr.se.stockmanagementapi.model.Transaction;

import java.util.function.Predicate;

public class TransactionDepotFilter implements Predicate<Transaction> {

    private final Long depotId;

    public TransactionDepotFilter(Long depotId) {
        this.depotId = depotId;
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction.getDepot().getId().equals(depotId);
    }
}
