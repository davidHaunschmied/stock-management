package pr.se.stockdataservice.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pr.se.stockdataservice.StockDataUpdater;

public class StockDataUpdateJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(StockDataUpdateJob.class);
    public static final String UPDATER_ID = "StockDataUpdater";
    private StockDataUpdater stockDataUpdater;

    @Override
    public void execute(JobExecutionContext context) {
        this.stockDataUpdater = (StockDataUpdater) context.getJobDetail().getJobDataMap().get(UPDATER_ID);
        log.info(String.format("Job %s is executed!", context.getJobDetail().getDescription()));
        updateStockData();
        updateHistoryData();
        checkAlerts();
    }

    private void checkAlerts() {
    }

    private void updateHistoryData() {
    }

    private void updateStockData() {
        stockDataUpdater.updateStockData();
    }
}
