package pr.se.stockapiclient.request;

import pr.se.stockapiclient.exceptions.APIRequestException;
import pr.se.stockapiclient.response.StockAPIResponse;

public abstract class StockAPIRequest {
    String apiKey = System.getProperty("stock_api_key");
    String apiBasePath = "https://www.worldtradingdata.com/api/v1";

    public R getData() {
        R response = this.sendRequest();
        if (response.getMessage() != null && response.getMessage().contains("Invalid API Key")) {
            throw new APIRequestException("Invalid API Key");
        }
        return response;
    }

    protected abstract R sendRequest();
}
