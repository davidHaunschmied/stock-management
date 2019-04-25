package pr.se.stockmanagementapi.payload;

import pr.se.stockmanagementapi.model.Stock;
import pr.se.stockmanagementapi.model.enums.AlarmType;

public class AlarmCreationRequest {
    private Stock stock;
    private AlarmType alarmType;
    private double price;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public AlarmType getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
