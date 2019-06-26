package pr.se.stockdataservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockdataservice.stockapiclient.request.ForexHistoryRequest;
import pr.se.stockdataservice.stockapiclient.request.ForexRequest;
import pr.se.stockdataservice.stockapiclient.response.ForexHistoryResponse;
import pr.se.stockdataservice.stockapiclient.response.ForexResponse;
import pr.se.stockmanagementapi.model.ForexHistory;
import pr.se.stockmanagementapi.model.enums.Currency;
import pr.se.stockmanagementapi.respository.ForexHistoryRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static pr.se.stockdataservice.stockapiclient.request.StockHistoryRequest.DATE_FORMAT;
import static pr.se.stockmanagementapi.util.TimeZoneUtils.TIME_ZONE;

@Component
public class ForexDataUpdater {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForexDataUpdater.class);

    private final ForexHistoryRepository forexHistoryRepository;
    private final SimpleDateFormat formatter;

    @Autowired
    public ForexDataUpdater(ForexHistoryRepository forexHistoryRepository) {
        this.forexHistoryRepository = forexHistoryRepository;
        this.formatter = new SimpleDateFormat(DATE_FORMAT);
        this.formatter.setTimeZone(TIME_ZONE);
    }

    public void updateForexData() {
        for (Currency currency : Currency.values()) {
            String symbol = currency.getSymbol();
            if (!symbol.equals(Currency.BASE_CURRENCY.getSymbol()) && !historyUpToDate(symbol)) {
                ForexHistoryRequest request = new ForexHistoryRequest(Currency.BASE_CURRENCY.getSymbol(), symbol);
                ForexHistoryResponse response = request.getData();
                saveHistoryResponse(response);
            }
        }

        // save current exchange rate
        ForexRequest forexRequest = new ForexRequest(Currency.BASE_CURRENCY.getSymbol());
        ForexResponse forexResponse = forexRequest.getData();
        for (Currency currency : Currency.values()) {
            String symbol = currency.getSymbol();
            if (!symbol.equals(Currency.BASE_CURRENCY.getSymbol())) {
                double currentExchangeRate = forexResponse.getData().get(symbol);
                ForexHistory current = new ForexHistory(Currency.BASE_CURRENCY.getSymbol(), symbol, ForexHistory.CURRENT_TIMESTAMP, currentExchangeRate);
                forexHistoryRepository.save(current);
            }
        }
    }

    // check if not already there
    private boolean historyUpToDate(String currency) {
        List<ForexHistory> history = forexHistoryRepository.findByBaseAndConvertTo(Currency.BASE_CURRENCY.getSymbol(), currency);
        Optional<ForexHistory> lastHistory = history.stream().max(Comparator.comparing(ForexHistory::getDateMillis));
        if (lastHistory.isPresent()) {
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DATE, -2); // -2 because  time of getDateFrom() is 00:00:00 and yesterday has current time
            return new Date(lastHistory.get().getDateMillis()).before(yesterday.getTime());
        }
        return false;
    }

    private void saveHistoryResponse(ForexHistoryResponse response) {
        for (String dateString : response.getHistory().keySet()) {
            try {
                long dateMillis = formatter.parse(dateString).getTime();
                ForexHistory forexHistory = new ForexHistory(response.getBaseSymbol(), response.getConvertToSymbol(), dateMillis, response.getHistory().get(dateString));
                forexHistoryRepository.save(forexHistory);
            } catch (ParseException e) {
                LOGGER.error(String.format("Could not save forex history for %s to %s ", Currency.BASE_CURRENCY.getSymbol(), response.getBaseSymbol()), e);
            }
        }
    }
}
