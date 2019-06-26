package pr.se.stockdataservice.stockapiclient.request;

import org.springframework.web.client.RestTemplate;
import pr.se.stockdataservice.stockapiclient.response.ForexResponse;

public class ForexRequest extends StockAPIRequest<ForexResponse> {
    private String symbol;

    public ForexRequest() {
    }

    public ForexRequest(String symbol) {
        this.symbol = symbol;
    }

    @Override
    protected ForexResponse sendRequest() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(getRequestUrl(), ForexResponse.class);
    }

    private String getRequestUrl() {
        return "" + APIPath.BASE_PATH + APIPath.FOREX + "?base=" + symbol + "&api_token=" + this.apiKey;
    }
}
