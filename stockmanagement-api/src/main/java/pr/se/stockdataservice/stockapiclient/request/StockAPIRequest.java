package pr.se.stockdataservice.stockapiclient.request;

import pr.se.stockdataservice.stockapiclient.exceptions.APIRequestException;
import pr.se.stockdataservice.stockapiclient.response.StockAPIResponse;

public abstract class StockAPIRequest<R extends StockAPIResponse> {
    String apiKey = "9L9IMFzQzY1h8DbW2qYMuZHuUgKKKe0faoSS8j1Ge3NHwpLvoR6Mgtlc6GJV";

    public R getData() {
        R response = this.sendRequest();
        if (response.getMessage() != null && response.getMessage().contains("Invalid API Key")) {
            throw new APIRequestException("Invalid API Key");
        }
        return response;
    }

    protected abstract R sendRequest();
}
