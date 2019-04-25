package pr.se.stockdataservice.stockapiclient.request;

import org.springframework.web.client.RestTemplate;
import pr.se.stockdataservice.stockapiclient.response.HistoryResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryRequest extends StockAPIRequest<HistoryResponse> {
    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private final String apiPath = "/history";

    private String symbol;
    private Date dateFrom;
    private Date dateTo;

    public HistoryRequest() {
    }

    public HistoryRequest(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public HistoryResponse sendRequest() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(getRequestUrl(), HistoryResponse.class);
    }

    private String getRequestUrl() {
        String requestUrl = this.apiBasePath + apiPath;
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
