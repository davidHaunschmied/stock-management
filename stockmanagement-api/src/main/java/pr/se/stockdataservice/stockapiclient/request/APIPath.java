package pr.se.stockdataservice.stockapiclient.request;

public enum APIPath {
    BASE_PATH("https://api.worldtradingdata.com/api/v1"),
    STOCK_HISTORY("/history"),
    SEARCH("/stock_search"),
    STOCK("/stock"),
    FOREX("/forex"),
    FOREX_HISTORY("/forex_history");

    private String path;

    APIPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path;
    }
}
