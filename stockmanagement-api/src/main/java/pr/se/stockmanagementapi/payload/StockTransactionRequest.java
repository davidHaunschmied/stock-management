package pr.se.stockmanagementapi.payload;

import pr.se.stockmanagementapi.model.enums.Currency;

import javax.validation.constraints.Positive;

public class StockTransactionRequest {
    private Long depotId;
    private Long stockId;
    private Currency currency;
    @Positive
    private int amount;
    @Positive
    private double price;

    public Long getDepotId() {
        return depotId;
    }

    public void setDepotId(Long depotId) {
        this.depotId = depotId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
