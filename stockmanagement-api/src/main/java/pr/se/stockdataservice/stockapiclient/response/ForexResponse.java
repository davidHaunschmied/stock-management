package pr.se.stockdataservice.stockapiclient.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForexResponse extends StockAPIResponse {
    private int symbols_returned;
    private String base;
    private Map<String, Double> data;

    public ForexResponse() {
        this.data = new HashMap<>();
    }

    public int getSymbols_returned() {
        return symbols_returned;
    }

    public void setSymbols_returned(int symbols_returned) {
        this.symbols_returned = symbols_returned;
    }

    public Map<String, Double> getData() {
        return data;
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StockResponse{" +
            "symbols_returned=" + symbols_returned +
            ", data=" + data +
            '}';
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}
