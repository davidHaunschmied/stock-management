package pr.se.stockmanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("pr.se")
@SpringBootApplication
public class StockmanagementApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockmanagementApiApplication.class, args);
    }

}
