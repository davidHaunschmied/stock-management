package pr.se.stockmanagementapi.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pr.se.stockmanagementapi.model.Transaction;
import pr.se.stockmanagementapi.model.enums.TransactionType;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByTransactionType(TransactionType transactionType);
}
