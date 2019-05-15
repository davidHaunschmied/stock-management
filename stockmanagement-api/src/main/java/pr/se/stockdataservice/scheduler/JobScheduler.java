package pr.se.stockdataservice.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockdataservice.AlarmNotifier;
import pr.se.stockdataservice.StockDataUpdater;
import pr.se.stockdataservice.StockHistoryDataUpdater;
import pr.se.stockdataservice.scheduler.job.StockDataUpdateJob;

@Component
public class JobScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobScheduler.class);
    private static final int INTERVAL_IN_MINUTES = 5;
    private StockDataUpdater stockDataUpdater;
    private AlarmNotifier alarmNotifier;
    private StockHistoryDataUpdater stockHistoryDataUpdater;

    @Autowired
    public JobScheduler(StockDataUpdater stockDataUpdater, AlarmNotifier alarmNotifier, StockHistoryDataUpdater stockHistoryDataUpdater) {
        this.stockDataUpdater = stockDataUpdater;
        this.alarmNotifier = alarmNotifier;
        this.stockHistoryDataUpdater = stockHistoryDataUpdater;
    }

    public void run() {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            JobDataMap data = new JobDataMap();
            data.put(StockDataUpdateJob.UPDATER_ID, stockDataUpdater);
            data.put(StockDataUpdateJob.ALARM_CHECKER_ID, alarmNotifier);
            data.put(StockDataUpdateJob.HISTORY_UPDATER_ID, stockHistoryDataUpdater);

            JobDetail job = JobBuilder.newJob(StockDataUpdateJob.class)
                .withIdentity("StockDataUpdateJob")
                .usingJobData(data)
                .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("StockDataUpdateTrigger")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMinutes(INTERVAL_IN_MINUTES)
                    .repeatForever())
                .build();

            // https://stackoverflow.com/a/51724460
            if (scheduler.checkExists(job.getKey())) {
                scheduler.deleteJob(job.getKey());
            }
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            LOGGER.error("Job could not be started!", e);
        }
    }
}
