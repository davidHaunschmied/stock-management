package pr.se.stockapiclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pr.se.stockapiclient.response.HistoryResponse;
import pr.se.stockapiclient.response.SearchResponse;
import pr.se.stockapiclient.response.StockResponse;
import pr.se.stockapiclient.request.HistoryRequest;
import pr.se.stockapiclient.request.SearchRequest;
import pr.se.stockapiclient.request.StockRequest;

/* Test class for testing api requests*/
public class StockApiClient  {
    private static final Logger log = LoggerFactory.getLogger(StockApiClient.class);

    public static void main(String args[]) {
        stockRequest();
        historyRequest();
        searchRequest();
    }

    private static void searchRequest() {
        SearchRequest searchRequest = new SearchRequest("VOEST");
        searchRequest.getStockExchanges().add("VSE");
        searchRequest.getStockExchanges().add("GER");
        SearchResponse response = searchRequest.getData();
        log.info(response.toString());
    }

    private static void historyRequest() {
        HistoryRequest historyRequest = new HistoryRequest("VOE.VI");
        HistoryResponse response = historyRequest.getData();
        log.info(response.toString());
    }

    private static void stockRequest() {
        StockRequest stockRequest = new StockRequest();
        stockRequest.addSymbol("VOE.VI");
        stockRequest.addSymbol("AAPL");
        StockResponse response = stockRequest.getData();
        log.info(response.toString());
    }
}
