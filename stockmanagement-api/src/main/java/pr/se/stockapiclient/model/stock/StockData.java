package pr.se.stockapiclient.model.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockData {
    private String symbol;
    private String name;
    private String currency;
    private float price;
  //  private float day_high;
  //  private float day_low;
    private float day_change;
    private float change_pct;
    private float close_yesterday;
    private long market_cap;
    private String stock_exchange_long;
    private String stock_exchange_short;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDay_change() {
        return day_change;
    }

    public void setDay_change(float day_change) {
        this.day_change = day_change;
    }

    public float getChange_pct() {
        return change_pct;
    }

    public void setChange_pct(float change_pct) {
        this.change_pct = change_pct;
    }

    public float getClose_yesterday() {
        return close_yesterday;
    }

    public void setClose_yesterday(float close_yesterday) {
        this.close_yesterday = close_yesterday;
    }

    public long getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(long market_cap) {
        this.market_cap = market_cap;
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
            ", day_change=" + day_change +
            ", change_pct=" + change_pct +
            ", close_yesterday=" + close_yesterday +
            ", market_cap=" + market_cap +
            ", stock_exchange_long='" + stock_exchange_long + '\'' +
            ", stock_exchange_short='" + stock_exchange_short + '\'' +
            '}';
    }
}
