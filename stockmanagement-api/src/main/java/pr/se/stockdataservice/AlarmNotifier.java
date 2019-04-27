package pr.se.stockdataservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockmanagementapi.model.Alarm;
import pr.se.stockmanagementapi.model.enums.AlarmType;
import pr.se.stockmanagementapi.respository.AlarmRepository;
import pr.se.stockmanagementapi.services.NotificationService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlarmNotifier {
    private final AlarmRepository alarmRepository;
    private NotificationService notificationService;


    @Autowired
    public AlarmNotifier(AlarmRepository alarmRepository, NotificationService notificationService) {
        this.alarmRepository = alarmRepository;
        this.notificationService = notificationService;
    }

    public List<Alarm> findAllAlarmsToFire() {
        return this.alarmRepository.findAll().stream().filter(alarm ->
            (alarm.getAlarmType() == AlarmType.OVER && alarm.getStock().getPrice() > alarm.getPrice())
                || (alarm.getAlarmType() == AlarmType.UNDER && alarm.getStock().getPrice() < alarm.getPrice()))
            .collect(Collectors.toList());
    }

    public void findAndNotify() {
        List<Alarm> alarmsToFire = findAllAlarmsToFire();
        if (!alarmsToFire.isEmpty()) {
            notificationService.notify("/topic/alarm/notify", alarmsToFire);
        }
    }
}
