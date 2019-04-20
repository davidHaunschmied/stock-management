package pr.se.stockapiclient.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pr.se.stockapiclient.stockdata.StockData;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse implements IStockAPIResponse {
    private int total_results;
    private StockData[] data;

    public SearchResponse() {
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public StockData[] getData() {
        return data;
    }

    public void setData(StockData[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SearchResponse{" +
            "total_results=" + total_results +
            ", data=" + Arrays.toString(data) +
            '}';
    }
}
