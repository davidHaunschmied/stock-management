package pr.se.stockmanagementapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pr.se.stockmanagementapi.model.audit.DateAudit;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(StockHistory.StockHistoryId.class)
@Table(name = "StockHistory")
public class StockHistory extends DateAudit {
    @Id
    @ManyToOne
    @JoinColumn(name = "stock_id")
    @JsonIgnore
    private Stock stock;

    @Id
    @Column(nullable = false)
    private long dateMillis;

    @Column(nullable = false)
    private double price;

    public StockHistory() {
    }

    public StockHistory(Stock stock, long dateMillis, double price) {
        this.stock = stock;
        this.dateMillis = dateMillis;
        this.price = price;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
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

    // needed because of composite id
    public static class StockHistoryId implements Serializable {
        private long stock;
        private long dateMillis;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            StockHistoryId that = (StockHistoryId) o;

            if (stock != that.stock) return false;
            return dateMillis == that.dateMillis;

        }

        @Override
        public int hashCode() {
            int result = (int) (stock ^ (stock >>> 32));
            result = 31 * result + (int) (dateMillis ^ (dateMillis >>> 32));
            return result;
        }
    }
}
