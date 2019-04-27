package pr.se.stockdataservice.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pr.se.stockdataservice.AlarmNotifier;
import pr.se.stockdataservice.StockDataUpdater;

public class StockDataUpdateJob implements Job {
    public static final String ALARM_CHECKER_ID = "AlarmChecker";

    public static final String UPDATER_ID = "StockDataUpdater";
    private static final Logger log = LoggerFactory.getLogger(StockDataUpdateJob.class);
    private StockDataUpdater stockDataUpdater;
    private AlarmNotifier alarmNotifier;

    @Override
    public void execute(JobExecutionContext context) {
        this.stockDataUpdater = (StockDataUpdater) context.getJobDetail().getJobDataMap().get(UPDATER_ID);
        log.info("Checking for stock updates and alarms");
        this.alarmNotifier = (AlarmNotifier) context.getJobDetail().getJobDataMap().get(ALARM_CHECKER_ID);
        updateStockData();
        updateHistoryData();
        checkAlarms();
    }

    private void checkAlarms() {
        alarmNotifier.findAndNotify();
    }

    private void updateHistoryData() {
        // not needed for now
    }

    private void updateStockData() {
        stockDataUpdater.updateStockData();
    }
}
