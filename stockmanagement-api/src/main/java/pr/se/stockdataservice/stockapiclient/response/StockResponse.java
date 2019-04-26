package pr.se.stockdataservice.stockapiclient.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pr.se.stockdataservice.stockapiclient.stockdata.StockDataDetail;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockResponse extends StockAPIResponse {
    private int symbols_returned;
    private List<StockDataDetail> data;

    public StockResponse() {
        this.data = new ArrayList<>();
    }

    public int getSymbols_returned() {
        return symbols_returned;
    }

    public void setSymbols_returned(int symbols_returned) {
        this.symbols_returned = symbols_returned;
    }

    public List<StockDataDetail> getData() {
        return data;
    }

    public void setData(List<StockDataDetail> data) {
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
