package pr.se.stockmanagementapi.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(ForexHistory.ForexHistoryId.class)
@Table(name = "ForexHistory")
public class ForexHistory {
    public static final long CURRENT_TIMESTAMP = 0;

    @Id
    @Column(nullable = false)
    private String base;

    @Id
    @Column(nullable = false)
    private String convertTo;

    @Id
    @Column(nullable = false)
    private long dateMillis;

    @Column(nullable = false)
    private double exchangeRate;

    public ForexHistory() {
    }

    public ForexHistory(String base, String convertTo, long dateMillis, double exchangeRate) {
        this.base = base;
        this.convertTo = convertTo;
        this.dateMillis = dateMillis;
        this.exchangeRate = exchangeRate;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getConvertTo() {
        return convertTo;
    }

    public void setConvertTo(String convertTo) {
        this.convertTo = convertTo;
    }

    public long getDateMillis() {
        return dateMillis;
    }

    public void setDateMillis(long dateMillis) {
        this.dateMillis = dateMillis;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    // needed because of composite id
    public static class ForexHistoryId implements Serializable {
        private String base;
        private String convertTo;
        private long dateMillis;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ForexHistoryId that = (ForexHistoryId) o;

            if (dateMillis != that.dateMillis) return false;
            if (!base.equals(that.base)) return false;
            return convertTo.equals(that.convertTo);
        }

        @Override
        public int hashCode() {
            int result = base.hashCode();
            result = 31 * result + convertTo.hashCode();
            result = 31 * result + (int) (dateMillis ^ (dateMillis >>> 32));
            return result;
        }
    }
}
