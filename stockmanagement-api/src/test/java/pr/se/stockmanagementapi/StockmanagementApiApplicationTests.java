package pr.se.stockmanagementapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockmanagementApiApplicationTests {

	@Test
	public void contextLoads() {
	    // Dummy test for simple server startup verification
	    assertThat(true).isTrue();
	}

}
