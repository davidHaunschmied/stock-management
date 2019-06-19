package pr.se.stockdataservice.stockapiclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pr.se.stockdataservice.stockapiclient.request.*;
import pr.se.stockdataservice.stockapiclient.response.*;
import pr.se.stockmanagementapi.model.StockExchange;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static pr.se.stockdataservice.stockapiclient.request.StockHistoryRequest.DATE_FORMAT;
import static pr.se.stockmanagementapi.util.TimeZoneUtils.TIME_ZONE;

public class StockApiClient {
    private static final Logger log = LoggerFactory.getLogger(StockApiClient.class);

    public static void main(String[] args) {
        stockRequest();
        forexRequest();
        historyRequest();
        searchRequest();
        forexHistoryRequest();
    }

    private static void searchRequest() {
        SearchRequest searchRequest = new SearchRequest("VOEST");
        searchRequest.getStockExchanges().add(new StockExchange("VSE", "Vienna Stock Exchange"));
        searchRequest.getStockExchanges().add(new StockExchange("GER", "Deutsche Börse XETRA"));
        SearchResponse response = searchRequest.getData();
        log.info(response.toString());
    }

    private static void historyRequest() {
        StockHistoryRequest stockHistoryRequest = new StockHistoryRequest("VOE.VI");
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        formatter.setTimeZone(TIME_ZONE);
        try {
            stockHistoryRequest.setDateFrom(formatter.parse("2019-01-01"));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        StockHistoryResponse response = stockHistoryRequest.getData();
        log.info(response.toString());
    }

    private static void forexHistoryRequest() {
        ForexHistoryRequest forexHistoryRequest = new ForexHistoryRequest("EUR", "USD");
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        formatter.setTimeZone(TIME_ZONE);
        ForexHistoryResponse response = forexHistoryRequest.getData();
        log.info(response.toString());
    }

    private static void stockRequest() {
        StockRequest stockRequest = new StockRequest();
        stockRequest.addSymbol("VOE.VI");
        stockRequest.addSymbol("AAPL");
        StockResponse response = stockRequest.getData();
        log.info(response.toString());
    }

    private static void forexRequest() {
        ForexRequest forexRequest = new ForexRequest("EUR");
        ForexResponse response = forexRequest.getData();
        log.info(response.toString());
    }
}
