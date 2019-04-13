package pr.se.stockapiclient.model.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pr.se.stockapiclient.model.IStockAPIResponse;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockResponse implements IStockAPIResponse {
    private int symbols_returned;
    private List<StockData> data;

    public StockResponse() {
        this.data = new ArrayList<>();
    }

    public int getSymbols_returned() {
        return symbols_returned;
    }

    public void setSymbols_returned(int symbols_returned) {
        this.symbols_returned = symbols_returned;
    }

    public List<StockData> getData() {
        return data;
    }

    public void setData(List<StockData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StockResponse{" +
            "symbols_returned=" + symbols_returned +
            ", data=" + data +
            '}';
    }
}
