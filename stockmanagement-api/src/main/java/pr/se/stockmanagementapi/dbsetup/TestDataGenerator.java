package pr.se.stockmanagementapi.dbsetup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.model.StockExchange;
import pr.se.stockmanagementapi.respository.DepotRepository;
import pr.se.stockmanagementapi.respository.StockExchangeRepository;
import pr.se.stockmanagementapi.respository.StockRepository;

@Component
public class TestDataGenerator {

    private final DepotRepository depotRepository;
    private final StockRepository stockRepository;
    private final StockExchangeRepository stockExchangeRepository;

    @Autowired
    public TestDataGenerator(DepotRepository depotRepository, StockRepository stockRepository, StockExchangeRepository stockExchangeRepository) {
        this.depotRepository = depotRepository;
        this.stockRepository = stockRepository;
        this.stockExchangeRepository = stockExchangeRepository;
    }

    public void fillDbWithTestData(){
        // Stock Exchanges
        StockExchange se = new StockExchange("VSE", "Vienna Stock Exchange");
        stockExchangeRepository.save(se);

        // Stocks
        Stock s = new Stock("VOE.VI", "voestalpine AG");
        s.setCurrency("EUR");
        s.setStockExchange(se);
        stockRepository.save(s);

        // Depots
        depotRepository.save(new Depot("Risikodepot"));
        depotRepository.save(new Depot("Sicherheitsdepot"));
        Depot d = new Depot("David's Depot");
        d.getStocks().add(s);
        depotRepository.save(d);
    }
}
