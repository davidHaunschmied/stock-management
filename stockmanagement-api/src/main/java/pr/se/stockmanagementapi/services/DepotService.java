package pr.se.stockmanagementapi.services;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pr.se.stockmanagementapi.exceptions.BadRequestException;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.Earning;
import pr.se.stockmanagementapi.model.Holding;
import pr.se.stockmanagementapi.model.Transaction;
import pr.se.stockmanagementapi.model.export.TransactionCsv;
import pr.se.stockmanagementapi.payload.ApiResponse;
import pr.se.stockmanagementapi.payload.HistoryPoint;
import pr.se.stockmanagementapi.respository.DepotRepository;
import pr.se.stockmanagementapi.util.CSVUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class DepotService {
    private final DepotRepository depotRepository;
    private final HoldingService holdingService;

    @Autowired
    public DepotService(DepotRepository depotRepository, HoldingService holdingService) {
        this.depotRepository = depotRepository;
        this.holdingService = holdingService;
    }

    public double calculateEarnings(long depotId) {
        return holdingService.allCurrentHoldings(depotId).stream().map(Holding::getEarnings).mapToDouble(earning -> earning.stream().mapToDouble(Earning::getEarnings).sum()).sum();
    }

    public Depot findDepotByIdOrThrow(long id) {
        return depotRepository.findById(id).orElseThrow(() -> new BadRequestException("Depot with id " + id + " not found!"));
    }


    public Map<Long, Double> getDepotHistory(long depotId) {
        Map<Long, Double> result = new TreeMap<>();
        for (Holding holding : holdingService.allCurrentHoldings(depotId)) {
            Map<Long, Double> holdingResult = holdingService.getHoldingHistory(holding);
            for (Map.Entry<Long, Double> entry : result.entrySet()) {
                if (holdingResult.containsKey(entry.getKey())) {
                    result.put(entry.getKey(), result.get(entry.getKey()) + holdingResult.get(entry.getKey()));
                    holdingResult.remove(entry.getKey());
                }
            }
            result.putAll(holdingResult);
        }
        return result;
    }

    public List<HistoryPoint> getDepotHistorySorted(long depotId) {
        return getDepotHistory(depotId).entrySet().stream().sorted(Map.Entry.comparingByKey())
            .map(e -> new HistoryPoint(e.getKey(), e.getValue())).collect(Collectors.toList());
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


    public ResponseEntity importCSV(MultipartFile file) throws Exception {
        String depotName = file.getName().replace(".csv", "");
        List<TransactionCsv> transactionCsv = CSVUtils.read(TransactionCsv.class, file.getInputStream());
        if (depotRepository.findDepotByName(depotName).isPresent()) {

            return new ResponseEntity<>(new ApiResponse(false, "Depot name already taken!"), HttpStatus.BAD_REQUEST);
        }
        Depot depot = new Depot(depotName);
        this.depotRepository.save(depot);

        return new ResponseEntity<>(depot, HttpStatus.CREATED);
    }
}
