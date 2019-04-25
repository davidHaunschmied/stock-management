package pr.se.stockmanagementapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pr.se.stockmanagementapi.exceptions.BadRequestException;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.Holding;
import pr.se.stockmanagementapi.respository.DepotRepository;
import pr.se.stockmanagementapi.respository.HoldingRepository;

import java.util.List;

@Service
public class DepotService {
    private final DepotRepository depotRepository;
    private final HoldingRepository holdingRepository;

    @Autowired
    public DepotService(DepotRepository depotRepository, HoldingRepository holdingRepository) {
        this.depotRepository = depotRepository;
        this.holdingRepository = holdingRepository;
    }

    public double calculateEarnings(long depotId) {
        return allCurrentHoldings(depotId).stream().mapToDouble(Holding::getEarning).sum();
    }

    public Depot findDepotByIdOrThrow(long id) {
        return depotRepository.findById(id).orElseThrow(() -> new BadRequestException("Depot with id " + id + " not found!"));
    }

    public List<Holding> allCurrentHoldings(long depotId) {
        return holdingRepository.findByDepot(findDepotByIdOrThrow(depotId));
    }
}
