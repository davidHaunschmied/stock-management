package pr.se.stockapiclient.request;

import org.springframework.web.client.RestTemplate;
import pr.se.stockapiclient.model.history.HistoryResponse;

public class HistoryRequest extends StockAPIRequest{
    private String symbol;
    private String apiPath = "/history";

    public HistoryRequest() {
        super();
    }

    public HistoryRequest(String symbol) {
        super();
        this.symbol = symbol;
    }

    @Override
    public HistoryResponse getData() {
        String requestUrl = this.apiBasePath + apiPath +"?symbol="+symbol+"&api_token="+this.apiKey;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(requestUrl, HistoryResponse.class);
    }
}
