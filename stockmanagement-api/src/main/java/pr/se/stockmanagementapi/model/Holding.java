package pr.se.stockmanagementapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import pr.se.stockmanagementapi.model.audit.DateAudit;
import pr.se.stockmanagementapi.model.enums.TransactionType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<Transaction> transactions;

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
        this.transactions = new ArrayList<>();
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

    public List<Transaction> getTransactions() {
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
}
