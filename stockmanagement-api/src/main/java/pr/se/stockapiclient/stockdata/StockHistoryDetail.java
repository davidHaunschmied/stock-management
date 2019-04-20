package pr.se.stockapiclient.stockdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockHistoryDetail {
    private float open;
    private float close;
    private float high;
    private float low;
    private long volume;

    public StockHistoryDetail() {
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "StockHistoryDetail{" +
            "open=" + open +
            ", close=" + close +
            ", high=" + high +
            ", low=" + low +
            ", volume=" + volume +
            '}';
    }
}
