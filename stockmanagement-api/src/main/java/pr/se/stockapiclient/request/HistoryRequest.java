package pr.se.stockapiclient.request;

import org.springframework.web.client.RestTemplate;
import pr.se.stockapiclient.response.HistoryResponse;

public class HistoryRequest extends StockAPIRequest {
    private String symbol;
    private String apiPath = "/history";

    public HistoryRequest() {

    }

    public HistoryRequest(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public HistoryResponse getData() {
        String requestUrl = this.apiBasePath + apiPath + "?symbol=" + symbol + "&api_token=" + this.apiKey;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(requestUrl, HistoryResponse.class);
    }
}
