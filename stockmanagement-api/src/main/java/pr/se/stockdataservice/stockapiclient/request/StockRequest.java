package pr.se.stockdataservice.stockapiclient.request;

import org.springframework.web.client.RestTemplate;
import pr.se.stockdataservice.stockapiclient.response.StockResponse;

import java.util.ArrayList;
import java.util.List;

public class StockRequest extends StockAPIRequest<StockResponse> {
    private final String apiPath = "/stock";
    private List<String> symbols;


    public StockRequest() {
        this.symbols = new ArrayList<>();
    }

    public StockRequest(List<String> symbols) {
        this.symbols = symbols;
    }

    @Override
    public StockResponse sendRequest() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(getRequestUrl(), StockResponse.class);
    }

    private String getRequestUrl() {
        return this.apiBasePath + apiPath + "?symbol=" + String.join(",", symbols) + "&api_token=" + this.apiKey;
    }

    public void addSymbol(String symbol){
        this.symbols.add(symbol);
    }
}
