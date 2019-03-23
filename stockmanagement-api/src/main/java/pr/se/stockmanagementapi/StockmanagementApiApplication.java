package pr.se.stockmanagementapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pr.se.stockmanagementapi.dbsetup.TestDataGenerator;

@SpringBootApplication
public class StockmanagementApiApplication {

    @Autowired
    public StockmanagementApiApplication(TestDataGenerator testDataGenerator) {
        testDataGenerator.fillDbWithTestData();
    }

    public static void main(String[] args) {
        SpringApplication.run(StockmanagementApiApplication.class, args);
    }

}
