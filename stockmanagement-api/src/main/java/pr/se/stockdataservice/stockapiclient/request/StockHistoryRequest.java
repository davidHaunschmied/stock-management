package pr.se.stockdataservice.stockapiclient.request;

import org.springframework.web.client.RestTemplate;
import pr.se.stockdataservice.stockapiclient.response.StockHistoryResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

import static pr.se.stockmanagementapi.util.TimeZoneUtils.TIME_ZONE;

public class StockHistoryRequest extends StockAPIRequest<StockHistoryResponse> {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private String symbol;
    private Date dateFrom;
    private Date dateTo;
    private SimpleDateFormat formatter;

    public StockHistoryRequest(String symbol) {
        this.symbol = symbol;
        this.formatter = new SimpleDateFormat(DATE_FORMAT);
        this.formatter.setTimeZone(TIME_ZONE);
    }

    @Override
    protected StockHistoryResponse sendRequest() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(getRequestUrl(), StockHistoryResponse.class);
    }

    private String getRequestUrl() {
        String requestUrl = "" + APIPath.BASE_PATH + APIPath.STOCK_HISTORY;
        requestUrl += "?symbol=" + symbol;
        requestUrl += "&api_token=" + this.apiKey;
        if (dateFrom != null) {
            requestUrl += "&date_from=" + formatter.format(dateFrom);
        }
        if (dateTo != null) {
            requestUrl += "&date_to=" + formatter.format(dateTo);
        }
        return requestUrl;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
