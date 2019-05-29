package pr.se.stockmanagementapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "Settings")
public class Settings {
    public static final long SETTINGS_ID = 1L;

    @Id
    @JsonIgnore
    private Long id;

    private double flatSellCharges;
    private double relativeSellCharges;
    private double flatPurchaseCharges;
    private double relativePurchaseCharges;

    public Settings() {
    }

    public Settings(double flatSellCharges, double relativeSellCharges, double flatPurchaseCharges, double relativePurchaseCharges) {
        this.id = SETTINGS_ID;
        this.flatSellCharges = flatSellCharges;
        this.relativeSellCharges = relativeSellCharges;
        this.flatPurchaseCharges = flatPurchaseCharges;
        this.relativePurchaseCharges = relativePurchaseCharges;
    }

    public Long getId() {
        return id;
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
