package pr.se.stockmanagementapi.payload;

import pr.se.stockmanagementapi.model.enums.AlarmType;

import javax.validation.constraints.Positive;

public class AlarmCreationRequest {
    private long stockId;
    private AlarmType alarmType;
    @Positive
    private double price;

    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
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
