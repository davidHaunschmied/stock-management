package pr.se.stockapiclient.stockdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockDataDetail extends StockData {
    //  private float day_high;
    //  private float day_low;
    private float day_change;
    private float change_pct;
    private float close_yesterday;
    private long market_cap;

    public StockDataDetail() {
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

    @Override
    public String toString() {
        return "StockDataDetail{" +
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
