package pr.se.stockdataservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockdataservice.stockapiclient.request.SearchRequest;
import pr.se.stockdataservice.stockapiclient.request.StockRequest;
import pr.se.stockdataservice.stockapiclient.response.SearchResponse;
import pr.se.stockdataservice.stockapiclient.response.StockResponse;
import pr.se.stockdataservice.stockapiclient.stockdata.StockData;
import pr.se.stockdataservice.stockapiclient.stockdata.StockDataDetail;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.model.StockExchange;
import pr.se.stockmanagementapi.respository.DepotRepository;
import pr.se.stockmanagementapi.respository.StockExchangeRepository;
import pr.se.stockmanagementapi.respository.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StockDataUpdater {
    private List<StockExchange> availableStockExchanges;

    private final DepotRepository depotRepository;
    private final StockRepository stockRepository;
    private final StockExchangeRepository stockExchangeRepository;

    @Autowired
    public StockDataUpdater(DepotRepository depotRepository, StockRepository stockRepository, StockExchangeRepository stockExchangeRepository) {
        this.depotRepository = depotRepository;
        this.stockRepository = stockRepository;
        this.stockExchangeRepository = stockExchangeRepository;

        initAvailableStockExchanges();
    }

    private void initAvailableStockExchanges() {
        this.availableStockExchanges = new ArrayList<>();
        this.availableStockExchanges.add(new StockExchange("VSE", "Vienna Stock Exchange"));
        this.availableStockExchanges.add(new StockExchange("WSE", "Warsaw Stock Exchange"));
        //this.availableStockExchanges.add(new StockExchange("GER", "Deutsche Börse XETRA"));
        //this.availableStockExchanges.add(new StockExchange("NASDAQ", "NASDAQ Stock Exchange"));

    }


    public void updateStockData() {
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
    }

    private void loadStockDetail(StockData[] stockDataList) {
        StockRequest stockRequest = new StockRequest();
        for (StockData stockData : stockDataList) {
            stockRequest.addSymbol(stockData.getSymbol());
        }
        StockResponse response = stockRequest.getData();
        saveAllStocks(response.getData());
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
