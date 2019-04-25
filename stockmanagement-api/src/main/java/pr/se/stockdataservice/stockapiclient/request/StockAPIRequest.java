package pr.se.stockdataservice.stockapiclient.request;

import pr.se.stockdataservice.stockapiclient.exceptions.APIRequestException;
import pr.se.stockdataservice.stockapiclient.response.StockAPIResponse;

public abstract class StockAPIRequest<R extends StockAPIResponse> {
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
