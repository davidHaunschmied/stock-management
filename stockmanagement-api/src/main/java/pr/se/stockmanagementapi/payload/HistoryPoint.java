package pr.se.stockmanagementapi.payload;

public class HistoryPoint {
    private long dateMillis;
    private double price;

    public HistoryPoint() {
    }

    public HistoryPoint(long dateMillis, double price) {
        this.dateMillis = dateMillis;
        this.price = price;
    }

    public long getDateMillis() {
        return dateMillis;
    }

    public void setDateMillis(long dateMillis) {
        this.dateMillis = dateMillis;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
