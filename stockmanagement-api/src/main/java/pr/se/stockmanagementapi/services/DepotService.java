package pr.se.stockmanagementapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pr.se.stockmanagementapi.exceptions.BadRequestException;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.Holding;
import pr.se.stockmanagementapi.respository.DepotRepository;
import pr.se.stockmanagementapi.respository.HoldingRepository;

@Service
public class DepotService {
    private final DepotRepository depotRepository;
    private final HoldingRepository holdingRepository;
    private final HoldingService holdingService;

    @Autowired
    public DepotService(DepotRepository depotRepository, HoldingRepository holdingRepository, HoldingService holdingService) {
        this.depotRepository = depotRepository;
        this.holdingRepository = holdingRepository;
        this.holdingService = holdingService;
    }

    public double calculateEarnings(long depotId) {
        return holdingService.allCurrentHoldings(depotId).stream().mapToDouble(Holding::getEarning).sum();
    }

    public Depot findDepotByIdOrThrow(long id) {
        return depotRepository.findById(id).orElseThrow(() -> new BadRequestException("Depot with id " + id + " not found!"));
    }
}
