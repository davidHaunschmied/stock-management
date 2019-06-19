package pr.se.stockdataservice.stockapiclient.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForexHistoryResponse extends StockAPIResponse {
    private String symbols;
    private Map<String, Double> history;

    public ForexHistoryResponse() {
        this.history = new HashMap<>();
    }

    public String getSymbols() {
        return symbols;
    }

    public void setSymbols(String symbols) {
        this.symbols = symbols;
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
            "symbols='" + symbols + '\'' +
            ", history=" + history +
            '}';
    }
}
