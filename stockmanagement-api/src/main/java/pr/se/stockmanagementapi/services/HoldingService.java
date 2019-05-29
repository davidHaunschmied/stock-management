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

import static pr.se.stockmanagementapi.util.TimeZoneUtils.TIME_ZONE;

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
        return holdingRepository.findByDepotId(depotId);
    }

    public Map<Long, Double> getHoldingHistory(Holding holding) {
        Map<Long, Double> result = new TreeMap<>();
        Calendar historyDay = dateOnlyCalendar(holding.getTransactions().first().getDate());
        Calendar today = dateOnlyCalendar(new Date());
        do {
            historyDay.add(Calendar.DATE, 1);
            StockHistory stockHistory;
            if (historyDay.equals(today)) {
                stockHistory = new StockHistory(holding.getStock(), historyDay.getTimeInMillis(), holding.getStock().getPrice());
            } else {
                stockHistory = stockHistoryService.findByStockIdAndTime(holding.getStock().getId(), historyDay.getTimeInMillis());
                if (stockHistory == null) {
                    continue;
                }
            }
            result.put(historyDay.getTimeInMillis(), calculateCurrentValue(historyDay.getTimeInMillis(), stockHistory.getPrice(), holding));
        } while (historyDay.before(today));
        return result;
    }

    private double calculateCurrentValue(long dateInMillis, double price, Holding holding) {
        double currentHoldingProfit = (holding.getAmountAt(dateInMillis) * price) - holding.getTotalPriceAt(dateInMillis);
        double currentEarnings = holding.getEarningsAt(dateInMillis);
        return currentHoldingProfit + currentEarnings;
    }

    private Calendar dateOnlyCalendar(Date date) {
        Calendar calendar = Calendar.getInstance(TIME_ZONE);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public List<HistoryPoint> getHoldingHistorySorted(long depotId, long stockId) {
        Holding holding = holdingRepository.findByDepotIdAndStockId(depotId, stockId).orElseThrow(() -> new BadRequestException("Holding with depotId " + depotId + " and stockId " + stockId + " not found!"));
        return getHoldingHistory(holding).entrySet().stream().sorted(Map.Entry.comparingByKey())
            .map(e -> new HistoryPoint(e.getKey(), e.getValue())).collect(Collectors.toList());
    }
}
