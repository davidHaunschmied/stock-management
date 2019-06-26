package pr.se.stockmanagementapi.payload;

import javax.validation.constraints.Positive;

public class StockTransactionRequest {
    private Long depotId;
    private Long stockId;
    @Positive
    private int amount;

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
}
