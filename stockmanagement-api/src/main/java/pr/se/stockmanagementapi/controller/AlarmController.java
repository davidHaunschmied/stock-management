package pr.se.stockmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import pr.se.stockmanagementapi.model.Alarm;
import pr.se.stockmanagementapi.payload.AlarmCreationRequest;
import pr.se.stockmanagementapi.payload.ApiResponse;
import pr.se.stockmanagementapi.respository.AlarmRepository;
import pr.se.stockmanagementapi.services.AlarmService;
import pr.se.stockmanagementapi.services.StockService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/alarms")
public class AlarmController {
    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;
    private final StockService stockService;

    @Autowired
    public AlarmController(AlarmRepository alarmRepository, AlarmService alarmService, StockService stockService) {
        this.alarmRepository = alarmRepository;
        this.alarmService = alarmService;
        this.stockService = stockService;
    }

    @GetMapping("/all")
    public List<Alarm> getAllAlarms() {
        return alarmRepository.findAll();
    }

    @GetMapping("/stock/{stockId}")
    public List<Alarm> getAllAlarmsByStockId(@PathVariable long stockId) {
        return alarmRepository.findAllByStockId(stockId);
    }

    @DeleteMapping("/delete/{alarmId}")
    public List<Alarm> deleteAlarm(@PathVariable Long alarmId) {
        Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(() -> new ResourceAccessException("Alarm does not exist!"));
        alarmRepository.delete(alarm);
        return getAllAlarmsToFire();
    }


    @GetMapping("/allfired")
    public List<Alarm> getAllAlarmsToFire() {
        return alarmService.findAllAlarmsToFire();
    }

    @PostMapping("/new")
    public ResponseEntity createNewAlarm(@Valid @RequestBody AlarmCreationRequest alarmCreationRequest) {
        Alarm alarm = new Alarm(stockService.findStockByIdOrThrow(alarmCreationRequest.getStockId()), alarmCreationRequest.getAlarmType(), alarmCreationRequest.getPrice());
        if (alarmRepository.findAll().contains(alarm)) {
            return new ResponseEntity<>(new ApiResponse(false, "Alarm already exists!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(alarmRepository.save(alarm), HttpStatus.CREATED);
    }
}
