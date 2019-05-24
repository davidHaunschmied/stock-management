package pr.se.stockmanagementapi.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pr.se.stockdataservice.AlarmNotifier;
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

    @Autowired
    public Initializer(DepotRepository depotRepository, StockDataUpdater stockDataUpdater, StockHistoryDataUpdater stockHistoryDataUpdater, HoldingRepository holdingRepository, StockRepository stockRepository, AlarmRepository alarmRepository, AlarmNotifier alarmNotifier) {
        this.depotRepository = depotRepository;
        this.stockDataUpdater = stockDataUpdater;
        this.stockHistoryDataUpdater = stockHistoryDataUpdater;
        this.holdingRepository = holdingRepository;
        this.stockRepository = stockRepository;
        this.alarmRepository = alarmRepository;
        this.alarmNotifier = alarmNotifier;
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
        JobScheduler jobScheduler = new JobScheduler(stockDataUpdater, alarmNotifier, stockHistoryDataUpdater);
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
        Stock stockBhd = stockRepository.findBySymbol("BHD.VI").orElseThrow(() -> new IllegalStateException("BHD.VI not available!"));
        Stock stockVoe = stockRepository.findBySymbol("VOE.VI").orElseThrow(() -> new IllegalStateException("VOE.VI not available!"));
        Stock stockPost = stockRepository.findBySymbol("POST.VI").orElseThrow(() -> new IllegalStateException("POST.VI not available!"));

        if (holdingExists(depot, stockBhd) || holdingExists(depot, stockVoe) || holdingExists(depot, stockPost)){
            return;
        }

        Holding holdingBhd = holdingRepository.findByDepotAndStock(depot, stockPost).orElse(new Holding(depot, stockBhd));
        Holding holdingVoe = holdingRepository.findByDepotAndStock(depot, stockPost).orElse(new Holding(depot, stockVoe));
        Holding holdingPost = holdingRepository.findByDepotAndStock(depot, stockPost).orElse(new Holding(depot, stockPost));

        Transaction transactionBhd1 = new Transaction(10, 76 * 10, dateFormat.parse("12.07.2018 19:56:37"), TransactionType.PURCHASE);
        Transaction transactionBhd2 = new Transaction(5, 76 * 5, dateFormat.parse("04.10.2018 09:15:47"), TransactionType.SALE);
        Transaction transactionPost1 = new Transaction(10, 33.1 * 10, dateFormat.parse("15.02.2019 19:56:37"), TransactionType.PURCHASE);
        Transaction transactionPost2 = new Transaction(6, 34.6 * 6, dateFormat.parse("09.04.2019 20:38:41"), TransactionType.SALE);
        Transaction transactionVoe1 = new Transaction(12, 27.53 * 12, dateFormat.parse("02.01.2019 12:10:57"), TransactionType.PURCHASE);
        Transaction transactionVoe2 = new Transaction(12, 29.67 * 12, dateFormat.parse("27.03.2019 10:01:33"), TransactionType.SALE);

        holdingBhd.addTransaction(transactionBhd1);
        holdingBhd.addTransaction(transactionBhd2);
        holdingVoe.addTransaction(transactionVoe1);
        holdingVoe.addTransaction(transactionVoe2);
        holdingPost.addTransaction(transactionPost1);
        holdingPost.addTransaction(transactionPost2);

        holdingRepository.save(holdingBhd);
        holdingRepository.save(holdingVoe);
        holdingRepository.save(holdingPost);
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
