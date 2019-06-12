package pr.se.stockmanagementapi.model.export;

import pr.se.stockmanagementapi.model.Transaction;
import pr.se.stockmanagementapi.model.enums.TransactionType;

import java.util.Date;

public class TransactionCsv {
    private int amount;
    private double price;
    private Date date;
    private String symbol;
    private TransactionType transactionType;

    public TransactionCsv(Transaction transaction) {
        this.amount = transaction.getAmount();
        this.price = transaction.getPrice();
        this.date = transaction.getDate();
        this.symbol = transaction.getHolding().getStock().getSymbol();
        this.transactionType = transaction.getTransactionType();
    }

    public TransactionCsv() {
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
