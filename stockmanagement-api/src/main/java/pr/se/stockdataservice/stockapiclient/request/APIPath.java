package pr.se.stockdataservice.stockapiclient.request;

public enum APIPath {
    BASE_PATH("https://www.worldtradingdata.com/api/v1"),
    HISTORY("/history"),
    SEARCH("/stock_search"),
    STOCK("/stock");

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
