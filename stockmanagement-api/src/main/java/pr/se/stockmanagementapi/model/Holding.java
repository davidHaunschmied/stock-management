package pr.se.stockmanagementapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import org.hibernate.annotations.SortComparator;
import pr.se.stockmanagementapi.model.audit.DateAudit;
import pr.se.stockmanagementapi.model.enums.TransactionType;
import pr.se.stockmanagementapi.util.TransactionComparator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Stream;

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
    @SortComparator(TransactionComparator.class)
    private SortedSet<Transaction> transactions;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "holding_id")
    private List<Earning> earnings;


    public Holding() {
    }

    public Holding(Depot depot, Stock stock) {
        this.depot = depot;
        this.stock = stock;
        this.amount = 0;
        this.totalPrice = 0;
        this.transactions = new TreeSet<>(new TransactionComparator());
        this.earnings = new ArrayList<>();
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

    public List<Earning> getEarnings() {
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

    public int getAmountAt(double dateMillis) {
        return getTransactionsStreamBefore(dateMillis).filter(transaction -> transaction.getTransactionType() == TransactionType.PURCHASE).mapToInt(Transaction::getAmount).sum()
            - getTransactionsStreamBefore(dateMillis).filter(transaction -> transaction.getTransactionType() == TransactionType.SALE).mapToInt(Transaction::getAmount).sum();
    }

    public double getTotalPriceAt(double dateMillis) {
        return getTransactionsStreamBefore(dateMillis).filter(transaction -> transaction.getTransactionType() == TransactionType.PURCHASE).mapToDouble(Transaction::getPrice).sum()
            - getTransactionsStreamBefore(dateMillis).filter(transaction -> transaction.getTransactionType() == TransactionType.SALE).mapToDouble(Transaction::getPrice).sum();
    }

    public double getPricePerStockAt(double dateMillis) {
        return getTotalPriceAt(dateMillis) / getAmountAt(dateMillis);
    }

    private Stream<Transaction> getTransactionsStreamBefore(double dateMillis) {
        return transactions.stream().filter(transaction -> transaction.getDate().getTime() < dateMillis);
    }

    public double getEarningsAt(long dateMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateMillis);
        return earnings.stream().filter(earning -> {
            Calendar earningCal = Calendar.getInstance();
            earningCal.setTime(earning.getDate());
            return calendar.get(Calendar.DAY_OF_YEAR) == earningCal.get(Calendar.DAY_OF_YEAR) &&
                calendar.get(Calendar.YEAR) == earningCal.get(Calendar.YEAR);
        }).mapToDouble(Earning::getEarnings).sum();
    }
}
