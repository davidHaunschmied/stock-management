package pr.se.stockapiclient.request;

import org.springframework.web.client.RestTemplate;
import pr.se.stockapiclient.response.SearchResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchRequest extends StockAPIRequest<SearchResponse> {
    private final String apiPath = "/stock_search";
    private String searchTerm;  // use empty search term to find all stocks (e.g. for Vienna Stock Exchange (VSE) )
    private SearchByOption searchBy;
    private List<String> stockExchanges;
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
    public SearchResponse sendRequest() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(getRequestUrl(), SearchResponse.class);
    }

    private String getRequestUrl() {
        String requestUrl = this.apiBasePath + apiPath;
        requestUrl += "?search_term=" + (searchTerm != null ? searchTerm : "");
        requestUrl += "&search_by=" + searchBy.getOption();
        requestUrl += "&page=" + page;
        if (this.stockExchanges.size() > 0) {
            requestUrl += "&stock_exchange=" + String.join(",", stockExchanges);
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

    public List<String> getStockExchanges() {
        return stockExchanges;
    }

    public void setStockExchanges(List<String> stockExchanges) {
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
