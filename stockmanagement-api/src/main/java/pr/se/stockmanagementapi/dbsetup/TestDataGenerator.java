package pr.se.stockmanagementapi.dbsetup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.respository.DepotRepository;

@Component
public class TestDataGenerator {

    private final DepotRepository depotRepository;

    @Autowired
    public TestDataGenerator(DepotRepository depotRepository) {
        this.depotRepository = depotRepository;
    }

    public void fillDbWithTestData() {
        // Create test depots if empty
        if (depotRepository.findAll().isEmpty()){
            depotRepository.save(new Depot("Risikodepot"));
            depotRepository.save(new Depot("Sicherheitsdepot"));
        }
    }

}
