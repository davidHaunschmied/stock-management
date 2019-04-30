package pr.se.stockmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.model.Holding;
import pr.se.stockmanagementapi.model.lightweights.DepotIdAndName;
import pr.se.stockmanagementapi.payload.ApiResponse;
import pr.se.stockmanagementapi.payload.DepotCreationRequest;
import pr.se.stockmanagementapi.respository.DepotRepository;
import pr.se.stockmanagementapi.services.DepotService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/depots")
public class DepotController {

    private final DepotRepository depotRepository;
    private final DepotService depotService;

    @Autowired
    public DepotController(DepotRepository depotRepository, DepotService depotService) {
        this.depotRepository = depotRepository;
        this.depotService = depotService;
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

    @DeleteMapping("/{depotId}")
    public void deleteDepot(@PathVariable long depotId) {
        depotRepository.deleteById(depotId);
    }

    @GetMapping("/{depotId}/earnings")
    public double getEarnings(@PathVariable long depotId) {
        return depotService.calculateEarnings(depotId);
    }

    @GetMapping("/{depotId}/holdings")
    public List<Holding> getHoldings(@PathVariable long depotId) {
        return depotService.allCurrentHoldings(depotId);
    }
}
