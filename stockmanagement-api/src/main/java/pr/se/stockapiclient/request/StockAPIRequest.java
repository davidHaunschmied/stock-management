package pr.se.stockapiclient.request;

import pr.se.stockapiclient.model.IStockAPIResponse;

public abstract class StockAPIRequest {
    protected String apiKey = "9L9IMFzQzY1h8DbW2qYMuZHuUgKKKe0faoSS8j1Ge3NHwpLvoR6Mgtlc6GJV";
    protected String apiBasePath = "https://www.worldtradingdata.com/api/v1";

    public abstract IStockAPIResponse getData();
}
