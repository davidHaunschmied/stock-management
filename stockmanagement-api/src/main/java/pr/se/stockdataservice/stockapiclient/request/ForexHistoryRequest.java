package pr.se.stockdataservice.stockapiclient.request;

import org.springframework.web.client.RestTemplate;
import pr.se.stockdataservice.stockapiclient.response.ForexHistoryResponse;

public class ForexHistoryRequest extends StockAPIRequest<ForexHistoryResponse> {
    private String base;
    private String convertTo;

    public ForexHistoryRequest(String base, String convertTo) {
        this.base = base;
        this.convertTo = convertTo;
    }

    @Override
    protected ForexHistoryResponse sendRequest() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(getRequestUrl(), ForexHistoryResponse.class);
    }

    private String getRequestUrl() {
        String requestUrl = "" + APIPath.BASE_PATH + APIPath.FOREX_HISTORY;
        requestUrl += "?base=" + base;
        requestUrl += "&convert_to=" + convertTo;
        requestUrl += "&api_token=" + this.apiKey;
        return requestUrl;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getConvertTo() {
        return convertTo;
    }

    public void setConvertTo(String convertTo) {
        this.convertTo = convertTo;
    }
}
