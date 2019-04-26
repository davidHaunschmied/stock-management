package pr.se.stockmanagementapi.dbsetup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockmanagementapi.model.Depot;
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

    public void fillDbWithTestData() {
        // Create test depots if empty
        if (depotRepository.findAll().isEmpty()){
            depotRepository.save(new Depot("Risikodepot"));
            depotRepository.save(new Depot("Sicherheitsdepot"));
        }
    }

}
