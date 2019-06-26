package pr.se.stockdataservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockdataservice.stockapiclient.request.StockHistoryRequest;
import pr.se.stockdataservice.stockapiclient.response.StockAPIResponse;
import pr.se.stockdataservice.stockapiclient.response.StockHistoryResponse;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.model.StockHistory;
import pr.se.stockmanagementapi.model.enums.Currency;
import pr.se.stockmanagementapi.respository.StockHistoryRepository;
import pr.se.stockmanagementapi.respository.StockRepository;
import pr.se.stockmanagementapi.services.ForexHistoryService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static pr.se.stockdataservice.stockapiclient.request.StockHistoryRequest.DATE_FORMAT;
import static pr.se.stockmanagementapi.util.TimeZoneUtils.TIME_ZONE;

@Component
public class StockHistoryDataUpdater {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockHistoryDataUpdater.class);
    private static final int AMOUNT_DAYS_BACK = 365;

    private final StockRepository stockRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final SimpleDateFormat formatter;
    private final ForexHistoryService forexHistoryService;


    @Autowired
    public StockHistoryDataUpdater(StockRepository stockRepository, StockHistoryRepository stockHistoryRepository, ForexHistoryService forexHistoryService) {
        this.stockRepository = stockRepository;
        this.stockHistoryRepository = stockHistoryRepository;
        this.forexHistoryService = forexHistoryService;

        this.formatter = new SimpleDateFormat(DATE_FORMAT);
        this.formatter.setTimeZone(TIME_ZONE);
    }

    public void updateStockHistoryData() {
        for (Stock stock : stockRepository.findAll()) {
            updateStockHistory(stock);
        }
    }

    private void updateStockHistory(Stock stock) {
        try {
            StockHistoryRequest request = new StockHistoryRequest(stock.getSymbol());
            request.setDateFrom(getLatestHistoryDate(stock));
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DATE, -2); // -2 because  time of getDateFrom() is 00:00:00 and yesterday has current time
            if (request.getDateFrom().before(yesterday.getTime())) {
                StockHistoryResponse response = request.getData();
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

    private void saveHistoryResponse(Stock stock, StockHistoryResponse response) {
        for (String dateString : response.getHistory().keySet()) {
            try {
                long dateMillis = formatter.parse(dateString).getTime();
                double price = response.getHistory().get(dateString).getClose();
                if (!stock.getCurrency().equals(StockAPIResponse.EMPTY_VALUE) && !stock.getCurrency().equals(Currency.BASE_CURRENCY.getSymbol())) {
                    price /= forexHistoryService.getCurrentExchangeRate(Currency.BASE_CURRENCY.getSymbol(), stock.getCurrency());
                }
                StockHistory stockHistory = new StockHistory(stock, dateMillis, price);
                stockHistoryRepository.save(stockHistory);
            } catch (ParseException e) {
                LOGGER.error(String.format("Could not save stock history for stock %s (%s) ", stock.getName(), stock.getSymbol()), e);
            }
        }
    }
}
