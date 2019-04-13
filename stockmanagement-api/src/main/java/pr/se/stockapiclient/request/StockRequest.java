package pr.se.stockapiclient.request;

import org.springframework.web.client.RestTemplate;
import pr.se.stockapiclient.model.stock.StockResponse;

import java.util.ArrayList;
import java.util.List;

public class StockRequest extends StockAPIRequest {
    private String apiPath = "/stock";
    private List<String> symbols;


    public StockRequest() {
        super();
        this.symbols = new ArrayList<>();
    }

    public StockRequest(List<String> symbols) {
        super();
        this.symbols = symbols;
    }

    @Override
    public StockResponse getData() {
        String requestUrl = this.apiBasePath + apiPath +"?symbol="+String.join(",", symbols)+"&api_token="+this.apiKey;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(requestUrl, StockResponse.class);
    }

    public void addSymbol(String symbol){
        this.symbols.add(symbol);
    }
}
