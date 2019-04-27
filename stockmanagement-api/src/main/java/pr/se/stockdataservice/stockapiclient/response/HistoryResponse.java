package pr.se.stockdataservice.stockapiclient.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pr.se.stockdataservice.stockapiclient.stockdata.StockHistoryDetail;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryResponse extends StockAPIResponse {
    private String name;
    private Map<String, StockHistoryDetail> history;

    public HistoryResponse() {
        this.history = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, StockHistoryDetail> getHistory() {
        return history;
    }

    public void setHistory(Map<String, StockHistoryDetail> history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "HistoryResponse{" +
            "name='" + name + '\'' +
            ", history=" + history +
            '}';
    }
}
