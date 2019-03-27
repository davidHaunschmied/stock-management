package pr.se.stockmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pr.se.stockmanagementapi.model.Depot;
import pr.se.stockmanagementapi.payload.ApiResponse;
import pr.se.stockmanagementapi.payload.DepotCreationRequest;
import pr.se.stockmanagementapi.respository.DepotRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/depots")
public class DepotController {

    private final DepotRepository depotRepository;

    @Autowired
    public DepotController(DepotRepository depotRepository) {
        this.depotRepository = depotRepository;
    }

    @GetMapping("/all")
    public List<Depot> getAllDepots() {
        return depotRepository.findAll();
    }

    @GetMapping("/{depotId}")
    public ResponseEntity<?> getDepot(@PathVariable long depotId) {
        return ResponseEntity.ok(depotRepository.findById(depotId));
    }

    @PostMapping("/new")
    public ResponseEntity<?> createNewDepot(@Valid @RequestBody DepotCreationRequest depotCreationRequest) {
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

}
