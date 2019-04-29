package pr.se.stockmanagementapi.model;

import pr.se.stockmanagementapi.model.audit.DateAudit;
import pr.se.stockmanagementapi.model.enums.AlarmType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Alarm")
public class Alarm extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(nullable = false)
    private AlarmType alarmType;

    @Column(nullable = false)
    private double price;

    public Alarm() {
    }

    public Alarm(Stock stock, AlarmType alarmType, double price) {
        this.stock = stock;
        this.alarmType = alarmType;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public AlarmType getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarm alarm = (Alarm) o;
        return Double.compare(alarm.price, price) == 0 &&
            Objects.equals(stock, alarm.stock) &&
            alarmType == alarm.alarmType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock, alarmType, price);
    }
}
