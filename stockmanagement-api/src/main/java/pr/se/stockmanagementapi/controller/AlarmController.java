package pr.se.stockmanagementapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pr.se.stockmanagementapi.model.Alarm;
import pr.se.stockmanagementapi.payload.AlarmCreationRequest;
import pr.se.stockmanagementapi.payload.ApiResponse;
import pr.se.stockmanagementapi.respository.AlarmRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/alarms")
public class AlarmController {
    private final AlarmRepository alarmRepository;

    @Autowired
    public AlarmController(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @GetMapping("/all")
    public List<Alarm> getAllDepots() {
        return alarmRepository.findAll();
    }

    @PostMapping("/new")
    public ResponseEntity createNewDepot(@Valid @RequestBody AlarmCreationRequest alarmCreationRequest) {
        Alarm alarm = new Alarm(alarmCreationRequest.getStock(), alarmCreationRequest.getAlarmType(), alarmCreationRequest.getPrice());
        if (alarmRepository.findAll().contains(alarm)) {
            return new ResponseEntity<>(new ApiResponse(false, "Alarm already exists!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(alarmRepository.save(alarm), HttpStatus.CREATED);
    }
}
