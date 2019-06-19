package pr.se.stockmanagementapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockmanagementapi.respository.ForexHistoryRepository;

@Component
public class ForexHistoryService {
    private final ForexHistoryRepository forexHistoryRepository;

    @Autowired
    public ForexHistoryService(ForexHistoryRepository forexHistoryRepository) {
        this.forexHistoryRepository = forexHistoryRepository;
    }
}
