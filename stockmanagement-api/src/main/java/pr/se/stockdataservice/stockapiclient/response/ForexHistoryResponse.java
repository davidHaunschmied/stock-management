package pr.se.stockdataservice.stockapiclient.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForexHistoryResponse extends StockAPIResponse {
    private String symbol;
    private Map<String, Double> history;

    public ForexHistoryResponse() {
        this.history = new HashMap<>();
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBaseSymbol() {
        return this.symbol.substring(0, 3);
    }

    public String getConvertToSymbol() {
        return this.symbol.substring(3, 6);
    }

    public Map<String, Double> getHistory() {
        return history;
    }

    public void setHistory(Map<String, Double> history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "StockHistoryResponse{" +
            "symbol='" + symbol + '\'' +
            ", history=" + history +
            '}';
    }
}
