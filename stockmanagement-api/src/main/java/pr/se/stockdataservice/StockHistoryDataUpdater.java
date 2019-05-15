package pr.se.stockdataservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockdataservice.stockapiclient.request.HistoryRequest;
import pr.se.stockdataservice.stockapiclient.response.HistoryResponse;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.model.StockHistory;
import pr.se.stockmanagementapi.respository.StockHistoryRepository;
import pr.se.stockmanagementapi.respository.StockRepository;

import java.text.ParseException;
import java.util.*;

@Component
public class StockHistoryDataUpdater {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockHistoryDataUpdater.class);
    private static final int AMOUNT_DAYS_BACK = 365;

    private final StockRepository stockRepository;
    private final StockHistoryRepository stockHistoryRepository;


    @Autowired
    public StockHistoryDataUpdater(StockRepository stockRepository, StockHistoryRepository stockHistoryRepository) {
        this.stockRepository = stockRepository;
        this.stockHistoryRepository = stockHistoryRepository;
    }

    public void updateStockHistoryData() {
        for (Stock stock : stockRepository.findAll()) {
            updateStockHistory(stock);
        }
    }

    private void updateStockHistory(Stock stock) {
        try {
            HistoryRequest request = new HistoryRequest(stock.getSymbol());
            request.setDateFrom(getLatestHistoryDate(stock));
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DATE, -2); // -2 because  time of getDateFrom() is 00:00:00 and yesterday has current time
            if (request.getDateFrom().before(yesterday.getTime())) {
                HistoryResponse response = request.getData();
                saveHistoryResponse(stock, response);
            }
        } catch (Exception e) {
            LOGGER.error(String.format("Could not update history for stock %s (%s) ", stock.getName(), stock.getSymbol()), e);
        }
    }

    private Date getLatestHistoryDate(Stock stock) {
        List<StockHistory> history = stockHistoryRepository.findByStockId(stock.getId());
        Optional<StockHistory> lastHistory = history.stream().max(Comparator.comparing(StockHistory::getDateMillis));
        if (lastHistory.isPresent()) {
            return new Date(lastHistory.get().getDateMillis());
        } else {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.DATE, -AMOUNT_DAYS_BACK);
            return now.getTime();
        }
    }

    private void saveHistoryResponse(Stock stock, HistoryResponse response) {
        for (String yearString : response.getHistory().keySet()) {
            try {
                long dateMillis = HistoryRequest.formatter.parse(yearString).getTime();
                StockHistory stockHistory = new StockHistory(stock, dateMillis, response.getHistory().get(yearString).getClose());
                stockHistoryRepository.save(stockHistory);
            } catch (ParseException e) {
                LOGGER.error(String.format("Could not save stock history for stock %s (%s) ", stock.getName(), stock.getSymbol()), e);
            }
        }
    }
}
