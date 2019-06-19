package pr.se.stockdataservice.stockapiclient.request;

import org.springframework.web.client.RestTemplate;
import pr.se.stockdataservice.stockapiclient.response.ForexHistoryResponse;

import java.text.SimpleDateFormat;

import static pr.se.stockmanagementapi.util.TimeZoneUtils.TIME_ZONE;

public class ForexHistoryRequest extends StockAPIRequest<ForexHistoryResponse> {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private String base;
    private String convertTo;
    private SimpleDateFormat formatter;

    public ForexHistoryRequest(String base, String convertTo) {
        this.base = base;
        this.convertTo = convertTo;
        this.formatter = new SimpleDateFormat(DATE_FORMAT);
        this.formatter.setTimeZone(TIME_ZONE);
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
