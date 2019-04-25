package pr.se.stockdataservice.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import pr.se.stockdataservice.StockDataUpdater;
import pr.se.stockdataservice.scheduler.job.StockDataUpdateJob;

public class JobScheduler {
    private final int intervalInSeconds = 5 * 60;
    private StockDataUpdater stockDataUpdater;

    public JobScheduler(StockDataUpdater stockDataUpdater) {
        this.stockDataUpdater = stockDataUpdater;
    }

    public JobScheduler() {
    }

    public static void main(String[] args) {
        JobScheduler jobScheduler = new JobScheduler();
        jobScheduler.fireJob();
    }

    public void fireJob() {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            JobDataMap data = new JobDataMap();
            data.put("updater", stockDataUpdater);

            JobDetail job = JobBuilder.newJob(StockDataUpdateJob.class)
                .withIdentity("StockDataUpdateJob")
                .usingJobData(data)
                .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("StockDataUpdateTrigger")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(intervalInSeconds)
                    .repeatForever())
                .build();

            scheduler.scheduleJob(job, trigger);

            /*
            try {
                Thread.sleep(1000 * 60 * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scheduler.shutdown();
            */
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
