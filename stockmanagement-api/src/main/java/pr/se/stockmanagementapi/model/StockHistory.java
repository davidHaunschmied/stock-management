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
    private double close;

    public StockHistory() {
    }

    public StockHistory(Stock stock, long dateMillis, double close) {
        this.stock = stock;
        this.dateMillis = dateMillis;
        this.close = close;
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

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    // needed because of composite id
    public static class StockHistoryId implements Serializable {
        private long stock;
        private long dateMillis;
    }
}
