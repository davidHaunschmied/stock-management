package pr.se.stockmanagementapi.services;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pr.se.stockmanagementapi.exceptions.BadRequestException;
import pr.se.stockmanagementapi.model.*;
import pr.se.stockmanagementapi.model.enums.TransactionType;
import pr.se.stockmanagementapi.model.export.TransactionCsv;
import pr.se.stockmanagementapi.payload.ApiResponse;
import pr.se.stockmanagementapi.payload.HistoryPoint;
import pr.se.stockmanagementapi.respository.DepotRepository;
import pr.se.stockmanagementapi.respository.HoldingRepository;
import pr.se.stockmanagementapi.respository.StockRepository;
import pr.se.stockmanagementapi.respository.TransactionRepository;
import pr.se.stockmanagementapi.util.CSVUtils;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepotService {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private final DepotRepository depotRepository;
    private final HoldingService holdingService;
    private final HoldingRepository holdingRepository;
    private final StockRepository stockRepository;
    private final ForexHistoryService forexHistoryService;
    private final TransactionRepository transactionRepository;

    @Autowired
    public DepotService(DepotRepository depotRepository, HoldingService holdingService, HoldingRepository holdingRepository, StockRepository stockRepository, ForexHistoryService forexHistoryService, TransactionRepository transactionRepository) {
        this.depotRepository = depotRepository;
        this.holdingService = holdingService;
        this.holdingRepository = holdingRepository;
        this.stockRepository = stockRepository;
        this.forexHistoryService = forexHistoryService;
        this.transactionRepository = transactionRepository;
    }

    public double calculateEarnings(long depotId) {
        return holdingService.allCurrentHoldings(depotId).stream().map(Holding::getEarnings).mapToDouble(earning -> earning.stream().mapToDouble(Earning::getEarnings).sum()).sum();
    }

    public Depot findDepotByIdOrThrow(long id) {
        return depotRepository.findById(id).orElseThrow(() -> new BadRequestException("Depot with id " + id + " not found!"));
    }

    @Cacheable("depotHistory")
    public Map<Long, Double> getDepotHistory(long depotId) {
        Map<Long, Double> depotHistory = new TreeMap<>();

        for (Holding holding : holdingService.allCurrentHoldings(depotId)) {
            Map<Long, Double> holdingResult = holdingService.getHoldingHistory(holding);
            for (Map.Entry<Long, Double> entry : depotHistory.entrySet()) {
                if (holdingResult.containsKey(entry.getKey())) {
                    depotHistory.put(entry.getKey(), depotHistory.get(entry.getKey()) + holdingResult.get(entry.getKey()));
                    holdingResult.remove(entry.getKey());
                }
            }
            depotHistory.putAll(holdingResult);
        }
        return depotHistory;
    }

    public List<HistoryPoint> getDepotHistorySorted(long depotId) {
        return getDepotHistory(depotId).entrySet().stream().sorted(Map.Entry.comparingByKey())
            .map(e -> new HistoryPoint(e.getKey(), e.getValue())).collect(Collectors.toList());
    }


    public List<Depot> getDepotsByStockId(long stockId) {
        List<Depot> depots = new ArrayList<>();
        holdingRepository.findAllByStockId(stockId).forEach(holding -> {
            if (holding.getAmount() > 0)
                depots.add(holding.getDepot());
        });
        return depots;
    }

    public void exportCSV(HttpServletResponse response, String depotName, List<Transaction> transactions) throws Exception {

        List<TransactionCsv> transactionsCsv = transactions.stream().map(TransactionCsv::new).collect(Collectors.toList());
        //set file name and content type
        String filename = depotName + ".csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<TransactionCsv> writer = new StatefulBeanToCsvBuilder<TransactionCsv>(response.getWriter())
            .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
            .withOrderedResults(false)
            .build();

        //write all users to csv file
        writer.write(transactionsCsv);

    }


    public ResponseEntity importCSV(String depotName, MultipartFile file) throws Exception {
        List<TransactionCsv> transactionCsv = CSVUtils.read(TransactionCsv.class, file.getInputStream());
        if (depotRepository.findDepotByName(depotName).isPresent()) {

            return new ResponseEntity<>(new ApiResponse(false, "Depot name already taken!"), HttpStatus.BAD_REQUEST);
        }
        Depot depot = new Depot(depotName);
        this.depotRepository.save(depot);

        for (TransactionCsv t : transactionCsv) {
            Stock stock = stockRepository.findBySymbol(t.getSymbol()).orElseThrow(() -> new BadRequestException("Stocksymbol not in CSV-File: " + t.getSymbol()));
            Holding holding = holdingRepository.findByDepotAndStock(depot, stock).orElse(new Holding(depot, stock));

            Transaction transaction = t.createTransaction();
            holding.addTransaction(transaction);
            holdingRepository.save(holding);
        }

        return new ResponseEntity<>(depot, HttpStatus.CREATED);
    }

    public double calculateCurrencyEarnings(long depotId, String currency) {
        double currentExchangeRate = forexHistoryService.getCurrentExchangeRateForEur(currency);
        List<Holding> holdings = holdingRepository.findByDepotId(depotId);
        double earningsBefore = 0;
        double totalDiff = 0;
        for (Holding holding : holdings) {
            Set<Transaction> transactions = holding.getTransactions();
            for (Transaction transaction : transactions) {
                if (transaction.getTransactionType() == TransactionType.SALE) {
                    double earnings = holding.getEarningsAt(transaction.getDate().getTime()) - earningsBefore;
                    double exchangeRateAtHistoryPoint = forexHistoryService.getCurrentExchangeRateForEurAndDate(currency, getTimestamp(transaction.getDate()));
                    double currentPriceConverted = earnings * currentExchangeRate;
                    double actualPriceConverted = earnings * exchangeRateAtHistoryPoint;
                    totalDiff += actualPriceConverted - currentPriceConverted;
                    earningsBefore = earnings + earningsBefore;
                }
            }
        }

        return totalDiff / currentExchangeRate;
    }

    private long getTimestamp(Date date) {
        return DateUtils.isSameDay(new Date(), date) ? ForexHistory.CURRENT_TIMESTAMP : date.getTime();
    }
}
