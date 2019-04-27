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
@Table(name = "Holding")
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

    @Column(nullable = false)
    private double earning;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "holding")
    @JsonIgnore
    private List<Transaction> transactions;


    public Holding() {
    }

    public Holding(Depot depot, Stock stock) {
        this.depot = depot;
        this.stock = stock;
        this.amount = 0;
        this.totalPrice = 0;
        this.earning = 0;
        this.transactions = new ArrayList<>();
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

    public double getEarning() {
        return earning;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction.getTransactionType() == TransactionType.PURCHASE) {
            amount += transaction.getAmount();
            totalPrice += transaction.getPrice();
        } else if (transaction.getTransactionType() == TransactionType.SALE) {
            Preconditions.checkArgument(transaction.getAmount() <= amount, "Transaction amount must not be greater than amount of holding!");
            final double investedPrice = transaction.getAmount() * (totalPrice / amount);
            earning += transaction.getPrice() - investedPrice;
            amount -= transaction.getAmount();
            totalPrice -= investedPrice;
            totalPrice = totalPrice > 0 ? totalPrice : 0;
        } else {
            throw new UnsupportedOperationException(TransactionType.class.getSimpleName() +" " + transaction.getTransactionType().name() + " unknown");
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

    @VisibleForTesting
    void setEarning(double earning) {
        this.earning = earning;
    }
}
