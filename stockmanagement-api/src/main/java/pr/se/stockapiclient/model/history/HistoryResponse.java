package pr.se.stockapiclient.model.history;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pr.se.stockapiclient.model.IStockAPIResponse;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryResponse implements IStockAPIResponse {
    private String name;
    private HashMap<String, HistoryDetail> history;

    public HistoryResponse() {
        this.history = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, HistoryDetail> getHistory() {
        return history;
    }

    public void setHistory(HashMap<String, HistoryDetail> history) {
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
