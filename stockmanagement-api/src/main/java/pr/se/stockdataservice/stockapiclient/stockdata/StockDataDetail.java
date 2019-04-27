package pr.se.stockdataservice.stockapiclient.stockdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockDataDetail extends StockData {
    private double day_high;
    private double day_low;
    private double day_change;
    private double change_pct;
    private double close_yesterday;
    private long market_cap;


    public double getDay_change() {
        return day_change;
    }

    public void setDay_change(String day_change) {
        try {
            this.day_change = Double.parseDouble(day_change);
        } catch (NumberFormatException e) {
            this.day_change = 0;
        }
    }

    public double getChange_pct() {
        return change_pct;
    }

    public void setChange_pct(String change_pct) {
        try {
            this.change_pct = Double.parseDouble(change_pct);
        } catch (NumberFormatException e) {
            this.change_pct = 0;
        }
    }

    public double getClose_yesterday() {
        return close_yesterday;
    }

    public void setClose_yesterday(String close_yesterday) {
        try {
            this.close_yesterday = Double.parseDouble(close_yesterday);
        } catch (NumberFormatException e) {
            this.close_yesterday = -1;
        }
    }

    public long getMarket_cap() {
        return market_cap;
    }

    public void setMarket_cap(String market_cap) {
        try {
            this.market_cap = Long.parseLong(market_cap);
        } catch (NumberFormatException e) {
            this.market_cap = -1;
        }
    }

    public double getDay_high() {
        return day_high;
    }

    public void setDay_high(String day_high) {
        try {
            this.day_high = Double.parseDouble(day_high);
        } catch (NumberFormatException e) {
            this.day_high = -1;
        }
    }

    public double getDay_low() {
        return day_low;
    }

    public void setDay_low(String day_low) {
        try {
            this.day_low = Double.parseDouble(day_low);
        } catch (NumberFormatException e) {
            this.day_low = -1;
        }
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
