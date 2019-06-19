package pr.se.stockdataservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockmanagementapi.respository.ForexHistoryRepository;

import java.text.SimpleDateFormat;

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

    }
}
