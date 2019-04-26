package pr.se.stockdataservice.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pr.se.stockdataservice.AlarmChecker;
import pr.se.stockdataservice.StockDataUpdater;
import pr.se.stockdataservice.scheduler.job.StockDataUpdateJob;
import pr.se.stockmanagementapi.services.NotificationService;

public class JobScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobScheduler.class);
    private static final int INTERVAL_MINUTES = 3;

    private StockDataUpdater stockDataUpdater;
    private NotificationService notificationService;
    private AlarmChecker alarmChecker;

    public JobScheduler(StockDataUpdater stockDataUpdater, NotificationService notificationService, AlarmChecker alarmChecker) {
        this.stockDataUpdater = stockDataUpdater;
        this.notificationService = notificationService;
        this.alarmChecker = alarmChecker;
    }

    public void fireJob() {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            JobDataMap data = new JobDataMap();
            data.put(StockDataUpdateJob.UPDATER_ID, stockDataUpdater);
            data.put(StockDataUpdateJob.NOTIFICATION_SERVICE_ID, notificationService);
            data.put(StockDataUpdateJob.ALARM_CHECKER_ID, alarmChecker);

            JobDetail job = JobBuilder.newJob(StockDataUpdateJob.class)
                .withIdentity("StockDataUpdateJob")
                .usingJobData(data)
                .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("StockDataUpdateTrigger")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMinutes(INTERVAL_MINUTES)
                    .repeatForever())
                .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            LOGGER.error("Job could not be started!", e);
        }
    }
}
