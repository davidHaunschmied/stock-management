package pr.se.stockmanagementapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pr.se.stockmanagementapi.model.Alarm;
import pr.se.stockmanagementapi.model.enums.AlarmType;
import pr.se.stockmanagementapi.respository.AlarmRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    public List<Alarm> findAllAlarmsToFire() {
        return this.alarmRepository.findAll().stream().filter(alarm ->
            (alarm.getAlarmType() == AlarmType.OVER && alarm.getStock().getPrice() > alarm.getPrice())
                || (alarm.getAlarmType() == AlarmType.UNDER && alarm.getStock().getPrice() < alarm.getPrice()))
            .collect(Collectors.toList());
    }
}
