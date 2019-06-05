package pr.se.stockmanagementapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import org.hibernate.annotations.SortComparator;
import pr.se.stockmanagementapi.model.audit.DateAudit;
import pr.se.stockmanagementapi.model.enums.TransactionType;
import pr.se.stockmanagementapi.util.DateComparator;

import javax.persistence.*;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "Holding",
    uniqueConstraints = @UniqueConstraint(columnNames = {"depot_id", "stock_id"})
)
public class Holding extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "depot_id")
    @JsonIgnore
    private Depot depot;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private double totalPrice;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "holding_id")
    @JsonIgnore
    @SortComparator(DateComparator.class)
    private SortedSet<Transaction> transactions;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "holding_id")
    @SortComparator(DateComparator.class)
    private SortedSet<Earning> earnings;


    public Holding() {
    }

    public Holding(Depot depot, Stock stock) {
        this.depot = depot;
        this.stock = stock;
        this.amount = 0;
        this.totalPrice = 0;
        this.transactions = new TreeSet<>(new DateComparator());
        this.earnings = new TreeSet<>(new DateComparator());
    }

    public Depot getDepot() {
        return depot;
    }

    public Stock getStock() {
        return stock;
    }

    public int getAmount() {
        return amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public SortedSet<Transaction> getTransactions() {
        return transactions;
    }

    public SortedSet<Earning> getEarnings() {
        return earnings;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction.getTransactionType() == TransactionType.PURCHASE) {
            this.amount += transaction.getAmount();
            this.totalPrice += transaction.getPrice();
        } else if (transaction.getTransactionType() == TransactionType.SALE) {
            Preconditions.checkArgument(transaction.getAmount() <= this.amount, "Transaction amount must not be greater than amount of holding!");
            final double investedPrice = transaction.getAmount() * (this.totalPrice / this.amount);
            this.amount -= transaction.getAmount();
            this.totalPrice -= investedPrice;
            final Earning earning = new Earning(transaction.getDate());
            earning.setEarnings(transaction.getPrice() - investedPrice);
            earnings.add(earning);
        } else {
            throw new UnsupportedOperationException(TransactionType.class.getSimpleName() + " " + transaction.getTransactionType().name() + " unknown");
        }
        this.transactions.add(transaction);
    }

    @VisibleForTesting
    void setAmount(int amount) {
        this.amount = amount;
    }

    @VisibleForTesting
    void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAmountAt(long dateMillis) {
        int amountAt = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getDate().getTime() <= dateMillis) {
                if (transaction.getTransactionType() == TransactionType.PURCHASE) {
                    amountAt += transaction.getAmount();
                } else if (transaction.getTransactionType() == TransactionType.SALE) {
                    amountAt -= transaction.getAmount();
                } else {
                    throw new UnsupportedOperationException("TransactionType " + transaction.getTransactionType() + "not supported!");
                }
            } else {
                return amountAt;
            }
        }
        return amountAt;
    }

    public double getTotalPriceAt(long dateMillis) {
        double totalPriceAt = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getDate().getTime() <= dateMillis) {
                if (transaction.getTransactionType() == TransactionType.PURCHASE) {
                    totalPriceAt += transaction.getPrice();
                } else if (transaction.getTransactionType() == TransactionType.SALE) {
                    totalPriceAt -= transaction.getPrice();
                    totalPriceAt = totalPriceAt > 0 ? totalPriceAt : 0;
                } else {
                    throw new UnsupportedOperationException("TransactionType " + transaction.getTransactionType() + "not supported!");
                }
            } else {
                return totalPriceAt;
            }
        }
        return totalPriceAt;
    }

    public double getPricePerStockAt(long dateMillis) {
        return getTotalPriceAt(dateMillis) / getAmountAt(dateMillis);
    }

    public double getEarningsAt(long dateMillis) {
        double earningsAt = 0;
        for (Earning earning : this.earnings) {
            if (earning.getDate().getTime() <= dateMillis) {
                earningsAt += earning.getEarnings();
            } else {
                return earningsAt;
            }
        }
        return earningsAt;
    }
}
