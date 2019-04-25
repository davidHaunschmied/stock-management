package pr.se.stockdataservice.scheduler.job;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import pr.se.stockdataservice.StockDataUpdater;

public class StockDataUpdateJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job is executed!");
        JobDetail jobDetail = context.getJobDetail();
        updateStockData(jobDetail);
        updateHistoryData();
        checkAlerts();
    }

    private void checkAlerts() {
    }

    private void updateHistoryData() {
    }

    private void updateStockData(JobDetail jobDetail) {
        StockDataUpdater updater = (StockDataUpdater) jobDetail.getJobDataMap().get("updater");
        updater.fillDbWithTestData();
    }
}
