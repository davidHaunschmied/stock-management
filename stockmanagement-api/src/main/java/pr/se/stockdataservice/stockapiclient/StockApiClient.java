package pr.se.stockdataservice.stockapiclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pr.se.stockdataservice.stockapiclient.request.HistoryRequest;
import pr.se.stockdataservice.stockapiclient.request.SearchRequest;
import pr.se.stockdataservice.stockapiclient.request.StockRequest;
import pr.se.stockdataservice.stockapiclient.response.HistoryResponse;
import pr.se.stockdataservice.stockapiclient.response.SearchResponse;
import pr.se.stockdataservice.stockapiclient.response.StockResponse;
import pr.se.stockmanagementapi.model.StockExchange;

import java.text.ParseException;

public class StockApiClient { 
    private static final Logger log = LoggerFactory.getLogger(StockApiClient.class);

    public static void main(String[] args) {
        stockRequest();
        historyRequest();
        searchRequest();
    }

    private static void searchRequest() {
        SearchRequest searchRequest = new SearchRequest("VOEST");
        searchRequest.getStockExchanges().add(new StockExchange("VSE", "Vienna Stock Exchange"));
        searchRequest.getStockExchanges().add(new StockExchange("GER", "Deutsche Börse XETRA"));
        SearchResponse response = searchRequest.getData();
        log.info(response.toString());
    }

    private static void historyRequest() {
        HistoryRequest historyRequest = new HistoryRequest("VOE.VI");
        try {
            historyRequest.setDateFrom(HistoryRequest.formatter.parse("2019-01-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
