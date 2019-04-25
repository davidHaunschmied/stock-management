package pr.se.stockmanagementapi.model;

import pr.se.stockmanagementapi.model.audit.DateAudit;
import pr.se.stockmanagementapi.model.enums.TransactionType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Transaction")
public class Transaction extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name="stock_id")
    private Stock stock;

    @ManyToOne
    @JoinColumn(name="depot_id")
    private Depot depot;

    @Column(nullable = false)
    private TransactionType transactionType;

    public Transaction() {
    }

    public Transaction(int amount, double price, Date date, Stock stock, Depot depot, TransactionType transactionType) {
        this.amount = amount;
        this.price = price;
        this.date = date;
        this.stock = stock;
        this.depot = depot;
        this.transactionType = transactionType;
    }

    public static Transaction purchase(int amount, double price, Date date, Stock stock, Depot depot) {
        return new Transaction(amount, price, date, stock, depot, TransactionType.PURCHASE);
    }

    public static Transaction sell(int amount, double price, Date date, Stock stock, Depot depot) {
        return new Transaction(amount, price, date, stock, depot, TransactionType.SALE);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
