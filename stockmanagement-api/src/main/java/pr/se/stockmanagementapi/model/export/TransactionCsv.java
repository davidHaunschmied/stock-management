package pr.se.stockmanagementapi.model.export;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pr.se.stockmanagementapi.model.Transaction;
import pr.se.stockmanagementapi.model.enums.TransactionType;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionCsv {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private int amount;
    private double price;
    private String date;
    private String symbol;
    private TransactionType transactionType;

    public TransactionCsv(Transaction transaction) {
        this.amount = transaction.getAmount();
        this.price = transaction.getPrice();
        this.date = dateFormat.format(transaction.getDate());
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public Transaction createTransaction() throws ParseException {
        return new Transaction(this.amount, this.price, dateFormat.parse(this.date), this.transactionType);
    }
}
