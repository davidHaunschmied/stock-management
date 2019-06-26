package pr.se.stockmanagementapi.payload;

public class SettingsChangeRequest {

    private double flatSellCharges;
    private double relativeSellCharges;
    private double flatPurchaseCharges;
    private double relativePurchaseCharges;

    public SettingsChangeRequest() {
    }

    public double getFlatSellCharges() {
        return flatSellCharges;
    }

    public void setFlatSellCharges(double flatSellCharges) {
        this.flatSellCharges = flatSellCharges;
    }

    public double getRelativeSellCharges() {
        return relativeSellCharges;
    }

    public void setRelativeSellCharges(double relativeSellCharges) {
        this.relativeSellCharges = relativeSellCharges;
    }

    public double getFlatPurchaseCharges() {
        return flatPurchaseCharges;
    }

    public void setFlatPurchaseCharges(double flatPurchaseCharges) {
        this.flatPurchaseCharges = flatPurchaseCharges;
    }

    public double getRelativePurchaseCharges() {
        return relativePurchaseCharges;
    }

    public void setRelativePurchaseCharges(double relativePurchaseCharges) {
        this.relativePurchaseCharges = relativePurchaseCharges;
    }
}
