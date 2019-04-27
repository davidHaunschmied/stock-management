package pr.se.stockdataservice.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pr.se.stockdataservice.AlarmChecker;
import pr.se.stockdataservice.StockDataUpdater;
import pr.se.stockmanagementapi.model.Alarm;
import pr.se.stockmanagementapi.services.NotificationService;

import java.util.List;

public class StockDataUpdateJob implements Job {
    public static final String ALARM_CHECKER_ID = "AlarmChecker";

    public static final String UPDATER_ID = "StockDataUpdater";
    public static final String NOTIFICATION_SERVICE_ID = "AlarmNotifier";
    private static final Logger log = LoggerFactory.getLogger(StockDataUpdateJob.class);
    private StockDataUpdater stockDataUpdater;
    private NotificationService notificationService;
    private AlarmChecker alarmChecker;

    @Override
    public void execute(JobExecutionContext context) {
        this.stockDataUpdater = (StockDataUpdater) context.getJobDetail().getJobDataMap().get(UPDATER_ID);
        log.info("Checking for stock updates and alarms");
        this.notificationService = (NotificationService) context.getJobDetail().getJobDataMap().get(NOTIFICATION_SERVICE_ID);
        this.alarmChecker = (AlarmChecker) context.getJobDetail().getJobDataMap().get(ALARM_CHECKER_ID);
        updateStockData();
        updateHistoryData();
        checkAlarms();
    }

    private void checkAlarms() {
        List<Alarm> alarmsToFire = alarmChecker.findAllAlarmsToFire();
        if (!alarmsToFire.isEmpty()) {
            notificationService.notify("/topic/alarm/notify", alarmsToFire);
        }
    }

    private void updateHistoryData() {
        // not needed for now
    }

    private void updateStockData() {
        stockDataUpdater.updateStockData();
    }
}
