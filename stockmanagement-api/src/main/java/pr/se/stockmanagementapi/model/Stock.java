package pr.se.stockmanagementapi.model;

import pr.se.stockdataservice.stockapiclient.stockdata.StockData;
import pr.se.stockmanagementapi.model.audit.DateAudit;

import javax.persistence.*;

@Entity
@Table(name = "Stock")
public class Stock extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String symbol;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, length = 20)
    private String currency;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "stockExchange_id")
    private StockExchange stockExchange;

    public Stock() {
    }

    public Stock(String symbol, String name, String currency, double price) {
        this.symbol = symbol;
        this.name = name;
        this.currency = currency;
        this.price = price;
    }

    public static Stock fromStockData(StockData stockData) {
        return new Stock(stockData.getSymbol(), stockData.getName(), stockData.getCurrency(), stockData.getPrice());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public StockExchange getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(StockExchange stockExchange) {
        this.stockExchange = stockExchange;
    }
}
