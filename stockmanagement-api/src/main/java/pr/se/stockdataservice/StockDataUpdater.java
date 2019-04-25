package pr.se.stockdataservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockdataservice.stockapiclient.request.SearchRequest;
import pr.se.stockdataservice.stockapiclient.response.SearchResponse;
import pr.se.stockdataservice.stockapiclient.stockdata.StockData;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.model.StockExchange;
import pr.se.stockmanagementapi.respository.DepotRepository;
import pr.se.stockmanagementapi.respository.StockExchangeRepository;
import pr.se.stockmanagementapi.respository.StockRepository;

import java.util.Optional;

@Component
public class StockDataUpdater {

    private final DepotRepository depotRepository;
    private final StockRepository stockRepository;
    private final StockExchangeRepository stockExchangeRepository;

    @Autowired
    public StockDataUpdater(DepotRepository depotRepository, StockRepository stockRepository, StockExchangeRepository stockExchangeRepository) {
        this.depotRepository = depotRepository;
        this.stockRepository = stockRepository;
        this.stockExchangeRepository = stockExchangeRepository;
    }

    // TODO: send request with Symbols to get details
    public void fillDbWithTestData() {
        // Fetch and save up to 100 stocks from Vienna Stock Exchange
        StockExchange se = new StockExchange("VSE", "Vienna Stock Exchange");
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.getStockExchanges().add(se.getShortName());
        saveAllStocks(searchRequest.getData(), se);
        searchRequest.setPage(2);
        saveAllStocks(searchRequest.getData(), se);

        // Create test depots if empty
        if (depotRepository.findAll().isEmpty()) {
            depotRepository.save(new Depot("Risikodepot"));
            depotRepository.save(new Depot("Sicherheitsdepot"));
        }
    }

    private void saveAllStocks(SearchResponse data, StockExchange stockExchange) {
        Optional<StockExchange> optionalStockExchange = stockExchangeRepository.findByShortName(stockExchange.getShortName());
        if (!optionalStockExchange.isPresent()) {
            stockExchangeRepository.save(stockExchange);
        }
        for (StockData stockData : data.getData()) {
            if (isValid(stockData)) {
                Optional<Stock> optionalStock = stockRepository.findBySymbol(stockData.getSymbol());
                Stock stock;
                if (optionalStock.isPresent()) {
                    stock = optionalStock.get();
                    stock.setPrice(stockData.getPrice());
                } else {
                    stock = Stock.fromStockData(stockData);
                    stock.setStockExchange(stockExchange);
                }
                stockRepository.save(stock);
            }
        }
    }

    private boolean isValid(StockData stockData) {
        return !(stockData.getName().contains("N/A") || stockData.getCurrency().contains("N/A") || stockData.getPrice() <= 0);
    }
}
