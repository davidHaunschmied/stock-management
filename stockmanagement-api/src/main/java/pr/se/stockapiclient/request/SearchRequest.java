package pr.se.stockapiclient.request;

import org.springframework.web.client.RestTemplate;
import pr.se.stockapiclient.response.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class SearchRequest extends StockAPIRequest {
    private String apiPath = "/stock_search";
    private String searchTerm;
    private SearchByOption searchBy;
    private List<String> stockExchanges;


    public SearchRequest() {
        this.stockExchanges = new ArrayList<>();
        this.searchBy = SearchByOption.SYMBOL_AND_NAME;
    }

    public SearchRequest(String searchTerm) {
        this();
        this.searchTerm = searchTerm;
    }

    @Override
    public SearchResponse getData() {
        requireNonNull(searchTerm, "SearchTerm must not be null!");
        String requestUrl = this.apiBasePath + apiPath;
        requestUrl += "?search_term=" + searchTerm;
        requestUrl += "&search_by=" + searchBy.getOption();
        if (this.stockExchanges.size() > 0) {
            requestUrl += "&stock_exchange=" + String.join(",", stockExchanges);
        }
        requestUrl += "&api_token=" + this.apiKey;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(requestUrl, SearchResponse.class);
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
