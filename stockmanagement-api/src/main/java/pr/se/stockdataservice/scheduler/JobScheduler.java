package pr.se.stockdataservice.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import pr.se.stockdataservice.StockDataUpdater;
import pr.se.stockdataservice.scheduler.job.StockDataUpdateJob;

public class JobScheduler {
    private final int intervalInMinutes = 5;
    private StockDataUpdater stockDataUpdater;

    public JobScheduler(StockDataUpdater stockDataUpdater) {
        this.stockDataUpdater = stockDataUpdater;
    }

    public void fireJob() {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            JobDataMap data = new JobDataMap();
            data.put(StockDataUpdateJob.UPDATER_ID, stockDataUpdater);

            JobDetail job = JobBuilder.newJob(StockDataUpdateJob.class)
                .withIdentity("StockDataUpdateJob")
                .usingJobData(data)
                .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("StockDataUpdateTrigger")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMinutes(intervalInMinutes)
                    .repeatForever())
                .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
