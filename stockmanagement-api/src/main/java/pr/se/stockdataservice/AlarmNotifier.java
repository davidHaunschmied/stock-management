package pr.se.stockdataservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockmanagementapi.model.Alarm;
import pr.se.stockmanagementapi.services.AlarmService;
import pr.se.stockmanagementapi.services.NotificationService;

import java.util.List;

@Component
public class AlarmNotifier {
    private final AlarmService alarmService;
    private NotificationService notificationService;


    @Autowired
    public AlarmNotifier(AlarmService alarmService, NotificationService notificationService) {
        this.alarmService = alarmService;
        this.notificationService = notificationService;
    }

    public void findAndNotify() {
        List<Alarm> alarmsToFire = alarmService.findAllAlarmsToFire();
        if (!alarmsToFire.isEmpty()) {
            notificationService.notify("/topic/alarm/notify", alarmsToFire);
        }
    }
}
