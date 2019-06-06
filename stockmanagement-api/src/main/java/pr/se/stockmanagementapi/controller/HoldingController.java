package pr.se.stockmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pr.se.stockmanagementapi.model.Holding;
import pr.se.stockmanagementapi.services.HoldingService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/holdings")
public class HoldingController {
    private final HoldingService holdingService;


    @Autowired
    public HoldingController(HoldingService holdingService) {
        this.holdingService = holdingService;
    }

    @GetMapping("/{depotId}")
    public List<Holding> getAll(@PathVariable long depotId) {
        return holdingService.allCurrentHoldings(depotId);
    }

    @GetMapping("/byStock/{stockId}")
    public List<Holding> getAllByStock(@PathVariable long stockId) {return holdingService.getAllByStockId(stockId); }

}
