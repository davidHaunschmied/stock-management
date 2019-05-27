package pr.se.stockmanagementapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Earning")
public class Earning implements HasDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private double earnings;

    @ManyToOne
    @JoinColumn(name = "holding_id")
    @JsonIgnore
    private Holding holding;

    public Earning() {
    }

    public Earning(Date date) {
        this.date = date;
        this.earnings = 0;
    }

    public Earning(Date date, double earnings) {
        this.date = date;
        this.earnings = earnings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public Holding getHolding() {
        return holding;
    }

    public void setHolding(Holding holding) {
        this.holding = holding;
    }
}
