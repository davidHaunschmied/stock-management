package pr.se.stockmanagementapi.model;

import pr.se.stockdataservice.stockapiclient.stockdata.StockDataDetail;
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

    @Column(nullable = false)
    private double day_high;

    @Column(nullable = false)
    private double day_low;

    @Column(nullable = false)
    private double day_change;

    @Column(nullable = false)
    private double change_pct;

    @Column(nullable = false)
    private double close_yesterday;

    @Column(nullable = false)
    private long market_cap;

    @ManyToOne
    @JoinColumn(name = "stockExchange_id")
    private StockExchange stockExchange;

    public Stock() {
    }

    private Stock(String symbol, String name, String currency, double price, double day_high, double day_low, double day_change, double change_pct, double close_yesterday, long market_cap) {
        this.symbol = symbol;
        this.name = name;
        this.currency = currency;
        this.price = price;
        this.day_high = day_high;
        this.day_low = day_low;
        this.day_change = day_change;
        this.change_pct = change_pct;
        this.close_yesterday = close_yesterday;
        this.market_cap = market_cap;
    }

    public static Stock fromStockData(StockDataDetail stockData) {
        return new Stock(stockData.getSymbol(), stockData.getName(), stockData.getCurrency(), stockData.getPrice(), stockData.getDay_high(), stockData.getDay_low(), stockData.getDay_change(), stockData.getChange_pct(), stockData.getClose_yesterday(), stockData.getMarket_cap());
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

    public double getDay_high() {
        return day_high;
    }

    public void setDay_high(double day_high) {
        this.day_high = day_high;
    }

    public double getDay_low() {
        return day_low;
    }

    public void setDay_low(double day_low) {
        this.day_low = day_low;
    }

    public double getDay_change() {
        return day_change;
    }

    public void setDay_change(double day_change) {
        this.day_change = day_change;
    }

    public double getChange_pct() {
        return change_pct;
    }

    public void setChange_pct(double change_pct) {
        this.change_pct = change_pct;
    }

    public double getClose_yesterday() {
        return close_yesterday;
    }

    public void setClose_yesterday(double close_yesterday) {
        this.close_yesterday = close_yesterday;
    }

    public long getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(long market_cap) {
        this.market_cap = market_cap;
    }
}
