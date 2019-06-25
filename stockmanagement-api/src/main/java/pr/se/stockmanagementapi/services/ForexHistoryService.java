package pr.se.stockmanagementapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockmanagementapi.model.ForexHistory;
import pr.se.stockmanagementapi.respository.ForexHistoryRepository;

import java.util.Optional;

@Component
public class ForexHistoryService {
    private final ForexHistoryRepository forexHistoryRepository;

    @Autowired
    public ForexHistoryService(ForexHistoryRepository forexHistoryRepository) {
        this.forexHistoryRepository = forexHistoryRepository;
    }

    public double getCurrentExchangeRate(String base, String convertTo) {
        Optional<ForexHistory> current = this.forexHistoryRepository.findByBaseAndConvertTo(base, convertTo).stream().filter(f -> f.getDateMillis() == ForexHistory.CURRENT_TIMESTAMP).findFirst();
        if (current.isPresent()) {
            return current.get().getExchangeRate();
        } else {
            throw new IllegalStateException(String.format("No current Exchange Rate given for %s to %s", base, convertTo));
        }
    }
}
