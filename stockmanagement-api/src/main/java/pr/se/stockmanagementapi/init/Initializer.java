package pr.se.stockmanagementapi.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pr.se.stockdataservice.AlarmNotifier;
import pr.se.stockdataservice.ForexDataUpdater;
import pr.se.stockdataservice.StockDataUpdater;
import pr.se.stockdataservice.StockHistoryDataUpdater;
import pr.se.stockdataservice.scheduler.JobScheduler;
import pr.se.stockmanagementapi.model.*;
import pr.se.stockmanagementapi.model.enums.AlarmType;
import pr.se.stockmanagementapi.model.enums.TransactionType;
import pr.se.stockmanagementapi.respository.AlarmRepository;
import pr.se.stockmanagementapi.respository.DepotRepository;
import pr.se.stockmanagementapi.respository.HoldingRepository;
import pr.se.stockmanagementapi.respository.StockRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class Initializer implements ApplicationRunner {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static final Logger LOGGER = LoggerFactory.getLogger(Initializer.class);

    private final DepotRepository depotRepository;
    private final HoldingRepository holdingRepository;
    private final StockRepository stockRepository;
    private final AlarmRepository alarmRepository;

    private final StockDataUpdater stockDataUpdater;
    private final StockHistoryDataUpdater stockHistoryDataUpdater;
    private final AlarmNotifier alarmNotifier;
    private final ForexDataUpdater forexDataUpdater;

    @Autowired
    public Initializer(DepotRepository depotRepository, StockDataUpdater stockDataUpdater, StockHistoryDataUpdater stockHistoryDataUpdater, HoldingRepository holdingRepository, StockRepository stockRepository, AlarmRepository alarmRepository, AlarmNotifier alarmNotifier, ForexDataUpdater forexDataUpdater) {
        this.depotRepository = depotRepository;
        this.stockDataUpdater = stockDataUpdater;
        this.stockHistoryDataUpdater = stockHistoryDataUpdater;
        this.holdingRepository = holdingRepository;
        this.stockRepository = stockRepository;
        this.alarmRepository = alarmRepository;
        this.alarmNotifier = alarmNotifier;
        this.forexDataUpdater = forexDataUpdater;
    }

    @Override
    public void run(ApplicationArguments args) {
        insertDepotsIfNotExist("Risikodepot", "Sicherheitsdepot");
        stockDataUpdater.updateStockData();
        try {
            generateHoldingsAndTransactions("Risikodepot");
        } catch (Exception e) {
            LOGGER.error("Error while generating test holdings and transactions");
        }
        JobScheduler jobScheduler = new JobScheduler(stockDataUpdater, alarmNotifier, stockHistoryDataUpdater, forexDataUpdater);
        jobScheduler.run();
    }

    private void insertDepotsIfNotExist(String... depots) {
        for (String depot : depots) {
            if (!depotRepository.findDepotByName(depot).isPresent()) {
                depotRepository.save(new Depot(depot));
            }
        }
    }

    private void generateHoldingsAndTransactions(String depotName) throws ParseException {
        Depot depot = depotRepository.findDepotByName(depotName).orElseThrow(() -> new IllegalArgumentException("No depot with name " + depotName + " available!"));
        Stock stockVoe = stockRepository.findBySymbol("VOE.VI").orElseThrow(() -> new IllegalStateException("VOE.VI not available!"));
        Stock stockPost = stockRepository.findBySymbol("POST.VI").orElseThrow(() -> new IllegalStateException("POST.VI not available!"));

        insertInstantAlarm(stockVoe, stockPost);
        insertHoldings(depot);
    }

    private void insertHoldings(Depot depot) throws ParseException {
        Stock stockAnd = stockRepository.findBySymbol("ANDR.VI").orElseThrow(() -> new IllegalStateException("ANDR.VI not available!"));
        Stock stockVoe = stockRepository.findBySymbol("VOE.VI").orElseThrow(() -> new IllegalStateException("VOE.VI not available!"));

        if (holdingExists(depot, stockAnd) || holdingExists(depot, stockVoe)){
            return;
        }

        Holding holdingAnd = holdingRepository.findByDepotAndStock(depot, stockAnd).orElse(new Holding(depot, stockAnd));
        Holding holdingVoe = holdingRepository.findByDepotAndStock(depot, stockVoe).orElse(new Holding(depot, stockVoe));

        Transaction transactionAnd1 = new Transaction(200, 43.52 * 200, dateFormat.parse("12.06.2018 19:56:37"), TransactionType.PURCHASE);
        Transaction transactionAnd2 = new Transaction(200, 44.88 * 200, dateFormat.parse("23.10.2018 09:15:47"), TransactionType.SALE);

        Transaction transactionVoe1 = new Transaction(100, 25.9 * 100, dateFormat.parse("02.01.2019 12:10:57"), TransactionType.PURCHASE);

        holdingAnd.addTransaction(transactionAnd1);
        holdingAnd.addTransaction(transactionAnd2);
        holdingVoe.addTransaction(transactionVoe1);

        holdingRepository.save(holdingAnd);
        holdingRepository.save(holdingVoe);
    }

    private void insertInstantAlarm(Stock stock, Stock stock2) {
        Alarm alarm = alarmRepository.findByStockAndAlarmType(stock, AlarmType.UNDER).orElse(new Alarm(stock, AlarmType.UNDER, 0));
        alarm.setPrice(stock.getPrice() + 2);
        Alarm alarm2 = alarmRepository.findByStockAndAlarmType(stock2, AlarmType.OVER).orElse(new Alarm(stock2, AlarmType.OVER, 0));
        alarm2.setPrice(stock2.getPrice() - 4);
        alarmRepository.save(alarm);
        alarmRepository.save(alarm2);
    }

    private boolean holdingExists(Depot depot, Stock stock) {
        return holdingRepository.findByDepotAndStock(depot, stock).isPresent();
    }
}
