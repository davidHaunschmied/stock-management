package pr.se.stockapiclient.request;

import pr.se.stockapiclient.response.IStockAPIResponse;

public abstract class StockAPIRequest {
    protected String apiKey = System.getProperty("stock_api_key");
    protected String apiBasePath = "https://www.worldtradingdata.com/api/v1";

    public abstract IStockAPIResponse getData();
}
