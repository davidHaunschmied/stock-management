package pr.se.stockmanagementapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import pr.se.stockdataservice.StockDataUpdater;
import pr.se.stockdataservice.scheduler.JobScheduler;
import pr.se.stockmanagementapi.dbsetup.TestDataGenerator;

@ComponentScan("pr.se")
@SpringBootApplication
public class StockmanagementApiApplication {

    @Autowired
    public StockmanagementApiApplication(TestDataGenerator testDataGenerator, StockDataUpdater stockDataUpdater) {
        testDataGenerator.fillDbWithTestData();
        JobScheduler scheduler = new JobScheduler(stockDataUpdater);
        scheduler.fireJob();
    }

    public static void main(String[] args) {
        SpringApplication.run(StockmanagementApiApplication.class, args);
    }

}
