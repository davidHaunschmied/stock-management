package pr.se.stockdataservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockdataservice.stockapiclient.request.SearchRequest;
import pr.se.stockdataservice.stockapiclient.request.StockRequest;
import pr.se.stockdataservice.stockapiclient.response.SearchResponse;
import pr.se.stockdataservice.stockapiclient.response.StockAPIResponse;
import pr.se.stockdataservice.stockapiclient.response.StockResponse;
import pr.se.stockdataservice.stockapiclient.stockdata.StockData;
import pr.se.stockdataservice.stockapiclient.stockdata.StockDataDetail;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.model.StockExchange;
import pr.se.stockmanagementapi.model.enums.Currency;
import pr.se.stockmanagementapi.respository.StockExchangeRepository;
import pr.se.stockmanagementapi.respository.StockRepository;
import pr.se.stockmanagementapi.services.ForexHistoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StockDataUpdater {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockDataUpdater.class);
    private List<StockExchange> availableStockExchanges;

    private final StockRepository stockRepository;
    private final StockExchangeRepository stockExchangeRepository;
    private final ForexHistoryService forexHistoryService;

    @Autowired
    public StockDataUpdater(StockRepository stockRepository, StockExchangeRepository stockExchangeRepository, ForexHistoryService forexHistoryService) {
        this.stockRepository = stockRepository;
        this.stockExchangeRepository = stockExchangeRepository;
        this.forexHistoryService = forexHistoryService;
        initAvailableStockExchanges();
    }

    private void initAvailableStockExchanges() {
        this.availableStockExchanges = new ArrayList<>();
        this.availableStockExchanges.add(new StockExchange("VSE", "Vienna Stock Exchange"));
        this.availableStockExchanges.add(new StockExchange("WSE", "Warsaw Stock Exchange"));

        // possible others:
        // this.availableStockExchanges.add(new StockExchange("GER", "Deutsche Börse XETRA"));
        // this.availableStockExchanges.add(new StockExchange("NASDAQ", "NASDAQ Stock Exchange"));

    }


    public void updateStockData() {
        try {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.getStockExchanges().addAll(availableStockExchanges);

            saveAllStockExchanges(availableStockExchanges);
            SearchResponse response = searchRequest.getData();

            int i = 1;
            do {
                loadStockDetail(response.getData());
                i++;
                searchRequest.setPage(i);
                response = searchRequest.getData();
            } while (i <= response.getTotal_pages());
        } catch (Exception e) {
            LOGGER.error("Could not update stock data!", e);
        }

    }

    private void loadStockDetail(StockData[] stockDataList) {
        StockRequest stockRequest = new StockRequest();
        for (StockData stockData : stockDataList) {
            stockRequest.addSymbol(stockData.getSymbol());
        }
        StockResponse response = stockRequest.getData();
        List<StockDataDetail> stocks = response.getData().stream().filter(stockDataDetail -> !stockDataDetail.getName().equals(StockAPIResponse.EMPTY_VALUE)).collect(Collectors.toList());
        for (StockDataDetail stock : stocks) {
            if (!stock.getCurrency().equals(StockAPIResponse.EMPTY_VALUE) && !stock.getCurrency().equals(Currency.BASE_CURRENCY.getSymbol())) {
                stock.setPrice(stock.getPrice() / forexHistoryService.getCurrentExchangeRate(Currency.BASE_CURRENCY.getSymbol(), stock.getCurrency()));
            }
        }
        saveAllStocks(stocks);
    }

    private void saveAllStockExchanges(List<StockExchange> stockExchanges) {
        for (StockExchange stockExchange : stockExchanges) {
            Optional<StockExchange> optionalStockExchange = stockExchangeRepository.findByShortName(stockExchange.getShortName());
            if (!optionalStockExchange.isPresent()) {
                stockExchangeRepository.save(stockExchange);
            }
        }
    }


    private void saveAllStocks(List<StockDataDetail> stockDataList) {
        for (StockDataDetail stockData : stockDataList) {
            if (isValid(stockData)) {
                Optional<Stock> optionalStock = stockRepository.findBySymbol(stockData.getSymbol());
                Stock stock;
                if (optionalStock.isPresent()) {
                    stock = optionalStock.get();
                    stock.setPrice(stockData.getPrice());
                } else {
                    stock = Stock.fromStockData(stockData);
                    stock.setStockExchange(stockExchangeRepository.findByShortName(stockData.getStock_exchange_short()).orElseThrow(() -> new IllegalStateException("StockExchange does not exist.")));
                }
                stockRepository.save(stock);
            }
        }
    }

    private boolean isValid(StockData stockData) {
        return !(stockData.getName().contains("N/A") || stockData.getCurrency().contains("N/A") || stockData.getPrice() <= 0);
    }
}
