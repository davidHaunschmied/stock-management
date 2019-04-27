package pr.se.stockdataservice.stockapiclient.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pr.se.stockdataservice.stockapiclient.stockdata.StockData;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse extends StockAPIResponse {
    private int total_results;
    private int total_pages;
    private int total_returned;
    private StockData[] data;

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
            ", total_pages=" + total_pages +
            ", total_returned=" + total_returned +
            ", data=" + Arrays.toString(data) +
            '}';
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_returned() {
        return total_returned;
    }

    public void setTotal_returned(int total_returned) {
        this.total_returned = total_returned;
    }

}
