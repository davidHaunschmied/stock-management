package pr.se.stockmanagementapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "StockExchange")
public class StockExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String shortName;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @OneToMany(mappedBy="stockExchange")
    @JsonIgnore
    private List<Stock> stocks;

    public StockExchange() {
        this.stocks = new ArrayList<>();
    }

    public StockExchange(String shortName, String name) {
        this.stocks = new ArrayList<>();
        this.shortName = shortName;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}
