package pr.se.stockdataservice.stockapiclient.request;

import org.springframework.web.client.RestTemplate;
import pr.se.stockdataservice.stockapiclient.response.SearchResponse;
import pr.se.stockmanagementapi.model.StockExchange;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchRequest extends StockAPIRequest<SearchResponse> {
    private final String apiPath = "/stock_search";
    private String searchTerm;  // use empty search term to find all stocks (e.g. for Vienna Stock Exchange (VSE) )
    private SearchByOption searchBy;
    private List<StockExchange> stockExchanges;
    private int page = 1;


    public SearchRequest() {
        this.stockExchanges = new ArrayList<>();
        this.searchBy = SearchByOption.SYMBOL_AND_NAME;
    }

    public SearchRequest(String searchTerm) {
        this();
        this.searchTerm = searchTerm;
    }

    @Override
    protected SearchResponse sendRequest() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(getRequestUrl(), SearchResponse.class);
    }

    private String getRequestUrl() {
        String requestUrl = this.apiBasePath + apiPath;
        requestUrl += "?search_term=" + (searchTerm != null ? searchTerm : "");
        requestUrl += "&search_by=" + searchBy.getOption();
        requestUrl += "&page=" + page;
        if (this.stockExchanges.size() > 0) {
            String shortNames = stockExchanges.stream().map(StockExchange::getShortName).collect(Collectors.joining(","));
            requestUrl += "&stock_exchange=" + shortNames;
        }
        requestUrl += "&api_token=" + this.apiKey;
        return requestUrl;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public SearchByOption getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(SearchByOption searchBy) {
        this.searchBy = searchBy;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<StockExchange> getStockExchanges() {
        return stockExchanges;
    }

    public void setStockExchanges(List<StockExchange> stockExchanges) {
        this.stockExchanges = stockExchanges;
    }

    public enum SearchByOption {
        SYMBOL("symbol"),
        NAME("name"),
        SYMBOL_AND_NAME("symbol,name");

        private String option;

        SearchByOption(String envUrl) {
            this.option = envUrl;
        }

        public String getOption() {
            return option;
        }
    }
}
