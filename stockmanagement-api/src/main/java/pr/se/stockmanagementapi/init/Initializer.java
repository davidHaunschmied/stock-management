package pr.se.stockmanagementapi.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pr.se.stockdataservice.AlarmNotifier;
import pr.se.stockdataservice.StockDataUpdater;
import pr.se.stockdataservice.scheduler.JobScheduler;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.Holding;
import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.model.Transaction;
import pr.se.stockmanagementapi.model.enums.TransactionType;
import pr.se.stockmanagementapi.respository.DepotRepository;
import pr.se.stockmanagementapi.respository.HoldingRepository;
import pr.se.stockmanagementapi.respository.StockRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class Initializer implements ApplicationRunner {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private final DepotRepository depotRepository;
    private final HoldingRepository holdingRepository;
    private final StockRepository stockRepository;

    private final StockDataUpdater stockDataUpdater;
    private final AlarmNotifier alarmNotifier;

    @Autowired
    public Initializer(DepotRepository depotRepository, StockDataUpdater stockDataUpdater, HoldingRepository holdingRepository, StockRepository stockRepository, AlarmNotifier alarmNotifier) {
        this.depotRepository = depotRepository;
        this.stockDataUpdater = stockDataUpdater;
        this.holdingRepository = holdingRepository;
        this.stockRepository = stockRepository;
        this.alarmNotifier = alarmNotifier;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        insertDepotsIfNotExist("Risikodepot", "Sicherheitsdepot");
        stockDataUpdater.updateStockData();
        generateHoldingsAndTransactions("Risikodepot");
        JobScheduler jobScheduler = new JobScheduler(stockDataUpdater, alarmNotifier);
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

        insertHoldingsForVoest(depot, stockVoe);
        insertHoldingsForPost(depot, stockPost);
    }

    private void insertHoldingsForPost(Depot depot, Stock stockPost) throws ParseException {
        if (holdingExists(depot, stockPost)) {
            return;
        }
        Holding holdingPost = holdingRepository.findByDepotAndStock(depot, stockPost).orElse(new Holding(depot, stockPost));
        Transaction transactionPost1 = new Transaction(300, 33.1 * 300, dateFormat.parse("15.02.2019 19:56:37"), TransactionType.PURCHASE);
        holdingPost.addTransaction(transactionPost1);
        Transaction transactionPost2 = new Transaction(150, 34.6 * 150, dateFormat.parse("09.04.2019 20:38:41"), TransactionType.SALE);
        holdingPost.addTransaction(transactionPost2);
        holdingRepository.save(holdingPost);

    }

    private void insertHoldingsForVoest(Depot depot, Stock stockVoe) throws ParseException {
        if (holdingExists(depot, stockVoe)) {
            return;
        }
        Holding holdingVoe = new Holding(depot, stockVoe);
        Transaction transactionVoe1 = new Transaction(150, 27.53 * 150, dateFormat.parse("02.01.2019 12:10:57"), TransactionType.PURCHASE);
        holdingVoe.addTransaction(transactionVoe1);
        Transaction transactionVoe2 = new Transaction(100, 29.67 * 100, dateFormat.parse("27.03.2019 10:01:33"), TransactionType.SALE);
        holdingVoe.addTransaction(transactionVoe2);
        holdingRepository.save(holdingVoe);
    }

    private boolean holdingExists(Depot depot, Stock stock) {
        return holdingRepository.findByDepotAndStock(depot, stock).isPresent();
    }
}
