package pr.se.stockapiclient.stockdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockData {
    protected String symbol;
    protected String name;
    protected String currency;
    protected double price;
    protected String stock_exchange_long;
    protected String stock_exchange_short;

    public StockData() {
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

    public String getStock_exchange_long() {
        return stock_exchange_long;
    }

    public void setStock_exchange_long(String stock_exchange_long) {
        this.stock_exchange_long = stock_exchange_long;
    }

    public String getStock_exchange_short() {
        return stock_exchange_short;
    }

    public void setStock_exchange_short(String stock_exchange_short) {
        this.stock_exchange_short = stock_exchange_short;
    }

    @Override
    public String toString() {
        return "StockData{" +
            "symbol='" + symbol + '\'' +
            ", name='" + name + '\'' +
            ", currency='" + currency + '\'' +
            ", price=" + price +
            ", stock_exchange_long='" + stock_exchange_long + '\'' +
            ", stock_exchange_short='" + stock_exchange_short + '\'' +
            '}';
    }
}
