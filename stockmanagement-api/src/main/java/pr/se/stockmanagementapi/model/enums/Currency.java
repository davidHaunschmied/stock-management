package pr.se.stockmanagementapi.model.enums;

public enum Currency {
    EUR("EUR"),
    PLN("PLN"),
    USD("USD"),
    BASE_CURRENCY("EUR");

    private String symbol;

    Currency(String currency) {
        this.symbol = currency;
    }

    public String getSymbol() {
        return symbol;
    }
}
