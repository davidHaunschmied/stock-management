package pr.se.stockmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.Transaction;
import pr.se.stockmanagementapi.model.lightweights.DepotIdAndName;
import pr.se.stockmanagementapi.payload.ApiResponse;
import pr.se.stockmanagementapi.payload.DepotCreationRequest;
import pr.se.stockmanagementapi.payload.HistoryPoint;
import pr.se.stockmanagementapi.respository.DepotRepository;
import pr.se.stockmanagementapi.services.DepotService;
import pr.se.stockmanagementapi.services.HoldingService;
import pr.se.stockmanagementapi.services.TransactionService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/depots")
public class DepotController {

    private final DepotRepository depotRepository;
    private final DepotService depotService;
    private final HoldingService holdingService;
    private final TransactionService transactionService;

    @Autowired
    public DepotController(DepotRepository depotRepository, DepotService depotService, HoldingService holdingService, TransactionService transactionService) {
        this.depotRepository = depotRepository;
        this.depotService = depotService;
        this.holdingService = holdingService;
        this.transactionService = transactionService;
    }

    @GetMapping("/all")
    public List<DepotIdAndName> getAllDepots() {
        return depotRepository.findAllDepotIdAndName();
    }

    @GetMapping("/{depotId}")
    public ResponseEntity<Optional<Depot>> getDepot(@PathVariable long depotId) {
        return ResponseEntity.ok(depotRepository.findById(depotId));
    }

    @PostMapping("/new")
    public ResponseEntity createNewDepot(@Valid @RequestBody DepotCreationRequest depotCreationRequest) {
        if (depotRepository.findDepotByName(depotCreationRequest.getName()).isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "Depot name already taken!"), HttpStatus.BAD_REQUEST);
        }
        Depot depot = new Depot(depotCreationRequest.getName());
        return new ResponseEntity<>(depotRepository.save(depot), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{depotId}")
    public void deleteDepot(@PathVariable long depotId) {
        depotRepository.deleteById(depotId);
    }

    @GetMapping("/{depotId}/earnings")
    public double getEarnings(@PathVariable long depotId) {
        return depotService.calculateEarnings(depotId);
    }

    @GetMapping("/{depotId}/holdings/history/{stockId}")
    public List<HistoryPoint> getHoldingHistory(@PathVariable long depotId, @PathVariable long stockId) {
        return holdingService.getHoldingHistorySorted(depotId, stockId);
    }

    @GetMapping("/{depotId}/history")
    public List<HistoryPoint> getHistory(@PathVariable long depotId) {
        return depotService.getDepotHistorySorted(depotId);
    }

    @GetMapping("/byStock/{stockId}")
    public List<Depot> getAllDepotsByStock(@PathVariable long stockId){return depotService.getDepotsByStockId(stockId);}
    @GetMapping("/export/{depotId}")
    public void exportCSV(HttpServletResponse response, @PathVariable long depotId) throws Exception {

        String depotName = this.depotService.findDepotByIdOrThrow(depotId).getName();
        List<Transaction> transactions = transactionService.getAllByDepotId(depotId);
        this.depotService.exportCSV(response, depotName, transactions);

    }

    @PostMapping(value = "/import", consumes = "multipart/form-data")
    public ResponseEntity uploadMultipart(@RequestParam("depotName") String depotName, @RequestParam("data") MultipartFile file) throws Exception {
        return depotService.importCSV(depotName, file);
    }

}
