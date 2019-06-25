package pr.se.stockmanagementapi.model;

import pr.se.stockmanagementapi.model.audit.DateAudit;
import pr.se.stockmanagementapi.model.enums.TransactionType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Transaction")
public class Transaction extends DateAudit implements HasDate {

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
    @JoinColumn(name = "holding_id")
    private Holding holding;

    @Column(nullable = false)
    private TransactionType transactionType;

    public Transaction() {
    }

    public Transaction(int amount, double price, Date date, TransactionType transactionType) {
        this.amount = amount;
        this.price = price;
        this.date = date;
        this.transactionType = transactionType;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Holding getHolding() {
        return holding;
    }
}
