package pr.se.stockmanagementapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pr.se.stockmanagementapi.exceptions.BadRequestException;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.Earning;
import pr.se.stockmanagementapi.model.Holding;
import pr.se.stockmanagementapi.payload.HistoryPoint;
import pr.se.stockmanagementapi.respository.DepotRepository;
import pr.se.stockmanagementapi.respository.HoldingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class DepotService {
    private final DepotRepository depotRepository;
    private final HoldingService holdingService;
    private final HoldingRepository holdingRepository;

    @Autowired
    public DepotService(DepotRepository depotRepository, HoldingService holdingService, HoldingRepository holdingRepository) {
        this.depotRepository = depotRepository;
        this.holdingService = holdingService;
        this.holdingRepository = holdingRepository;
    }

    public double calculateEarnings(long depotId) {
        return holdingService.allCurrentHoldings(depotId).stream().map(Holding::getEarnings).mapToDouble(earning -> earning.stream().mapToDouble(Earning::getEarnings).sum()).sum();
    }

    public Depot findDepotByIdOrThrow(long id) {
        return depotRepository.findById(id).orElseThrow(() -> new BadRequestException("Depot with id " + id + " not found!"));
    }


    public Map<Long, Double> getDepotHistory(long depotId) {
        Map<Long, Double> result = new TreeMap<>();
        for (Holding holding : holdingService.allCurrentHoldings(depotId)) {
            Map<Long, Double> holdingResult = holdingService.getHoldingHistory(holding);
            for (Map.Entry<Long, Double> entry : result.entrySet()) {
                if (holdingResult.containsKey(entry.getKey())) {
                    result.put(entry.getKey(), result.get(entry.getKey()) + holdingResult.get(entry.getKey()));
                    holdingResult.remove(entry.getKey());
                }
            }
            result.putAll(holdingResult);
        }
        return result;
    }

    public List<HistoryPoint> getDepotHistorySorted(long depotId) {
        return getDepotHistory(depotId).entrySet().stream().sorted(Map.Entry.comparingByKey())
            .map(e -> new HistoryPoint(e.getKey(), e.getValue())).collect(Collectors.toList());
    }


    public List<Depot> getDepotsByStockId(long stockId){
        List<Depot> depots = new ArrayList<>();
        holdingRepository.findAllByStockId(stockId).forEach(holding -> {
            if(holding.getAmount() > 0)
                depots.add(holding.getDepot());
        });
        return depots;
    }
}
