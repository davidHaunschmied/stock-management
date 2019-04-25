package pr.se.stockdataservice.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import pr.se.stockdataservice.StockDataUpdater;

public class StockDataUpdateJob implements Job {
    public static final String UPDATER_ID = "StockDataUpdater";
    private StockDataUpdater stockDataUpdater;

    @Override
    public void execute(JobExecutionContext context) {
        this.stockDataUpdater = (StockDataUpdater) context.getJobDetail().getJobDataMap().get(UPDATER_ID);
        System.out.println(this.getClass() + " is executed!");
        updateStockData();
        updateHistoryData();
        checkAlerts();
    }

    private void checkAlerts() {
    }

    private void updateHistoryData() {
    }

    private void updateStockData() {
        stockDataUpdater.fillDbWithTestData();
    }
}
