package pr.se.stockdataservice;

import org.springframework.stereotype.Component;
import pr.se.stockmanagementapi.model.Alarm;
import pr.se.stockmanagementapi.model.enums.AlarmType;
import pr.se.stockmanagementapi.respository.AlarmRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlarmChecker {
    private final AlarmRepository alarmRepository;


    public AlarmChecker(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    public List<Alarm> findAllAlarmsToFire() {
        return this.alarmRepository.findAll().stream().filter(alarm ->
            (alarm.getAlarmType() == AlarmType.OVER && alarm.getStock().getPrice() > alarm.getPrice())
                || (alarm.getAlarmType() == AlarmType.UNDER && alarm.getStock().getPrice() < alarm.getPrice()))
            .collect(Collectors.toList());
    }
}
