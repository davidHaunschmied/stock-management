package pr.se.stockmanagementapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pr.se.stockmanagementapi.model.Holding;
import pr.se.stockmanagementapi.respository.HoldingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HoldingService {
    private final HoldingRepository holdingRepository;

    @Autowired
    public HoldingService(HoldingRepository holdingRepository) {
        this.holdingRepository = holdingRepository;
    }

    public List<Holding> allCurrentHoldings(long depotId) {
        return holdingRepository.findByDepotId(depotId).stream().filter(holding -> holding.getAmount() > 0).collect(Collectors.toList());
    }
}
