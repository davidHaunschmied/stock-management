package pr.se.stockdataservice.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pr.se.stockdataservice.AlarmNotifier;
import pr.se.stockdataservice.StockDataUpdater;
import pr.se.stockdataservice.scheduler.job.StockDataUpdateJob;

@Component
public class JobScheduler {
    private static final Logger log = LoggerFactory.getLogger(JobScheduler.class);
    private static final int INTERVAL_IN_MINUTES = 3;
    private StockDataUpdater stockDataUpdater;
    private AlarmNotifier alarmNotifier;

    @Autowired
    public JobScheduler(StockDataUpdater stockDataUpdater, AlarmNotifier alarmNotifier) {
        this.stockDataUpdater = stockDataUpdater;
        this.alarmNotifier = alarmNotifier;
    }

    public void run() {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            JobDataMap data = new JobDataMap();
            data.put(StockDataUpdateJob.UPDATER_ID, stockDataUpdater);
            data.put(StockDataUpdateJob.ALARM_CHECKER_ID, alarmNotifier);

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
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            log.error("Job could not be started!", e);
        }
    }
}
