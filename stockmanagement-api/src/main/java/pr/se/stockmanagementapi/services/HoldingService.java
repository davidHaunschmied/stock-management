package pr.se.stockmanagementapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pr.se.stockmanagementapi.exceptions.BadRequestException;
import pr.se.stockmanagementapi.model.Holding;
import pr.se.stockmanagementapi.model.StockHistory;
import pr.se.stockmanagementapi.payload.HistoryPoint;
import pr.se.stockmanagementapi.respository.HoldingRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HoldingService {
    private final HoldingRepository holdingRepository;
    private final StockHistoryService stockHistoryService;

    @Autowired
    public HoldingService(HoldingRepository holdingRepository, StockHistoryService stockHistoryService) {
        this.holdingRepository = holdingRepository;
        this.stockHistoryService = stockHistoryService;
    }

    public List<Holding> allCurrentHoldings(long depotId) {
        return holdingRepository.findByDepotId(depotId).stream().filter(holding -> holding.getAmount() > 0).collect(Collectors.toList());
    }

    public Map<Long, Double> getHoldingHistory(Holding holding) {
        Map<Long, Double> result = new TreeMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(holding.getTransactions().first().getDate());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        while (calendar.getTime().before(new Date())) {
            StockHistory stockHistory = stockHistoryService.findByStockIdAndTime(holding.getStock().getId(), calendar.getTimeInMillis())
                .orElse(new StockHistory(holding.getStock(), calendar.getTimeInMillis(), holding.getTotalPrice() / holding.getAmount()));
            double currentHoldingProfit = holding.getAmountAt(stockHistory.getDateMillis()) * stockHistory.getPrice() - holding.getTotalPriceAt(stockHistory.getDateMillis());
            double earnings = holding.getEarningsAt(stockHistory.getDateMillis());
            result.put(calendar.getTimeInMillis(), currentHoldingProfit + earnings);
            calendar.add(Calendar.DATE, 1);
        }
        return result;
    }

    public List<HistoryPoint> getHoldingHistorySorted(long depotId, long stockId) {
        Holding holding = holdingRepository.findByDepotIdAndStockId(depotId, stockId).orElseThrow(() -> new BadRequestException("Holding with depotId " + depotId + " and stockId " + stockId + " not found!"));
        return getHoldingHistory(holding).entrySet().stream().sorted(Map.Entry.comparingByKey())
            .map(e -> new HistoryPoint(e.getKey(), e.getValue())).collect(Collectors.toList());
    }

    public List<Holding> getAllByStockId(long stockId){
        return holdingRepository.findAllByStockId(stockId);
    }
}
