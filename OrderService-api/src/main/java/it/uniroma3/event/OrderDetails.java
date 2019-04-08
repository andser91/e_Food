package it.uniroma3.event;

import java.util.List;

public class OrderDetails {

    private List<LineItem> lineItems;
    private long restaurantId;
    private long consumerId;

    //constructs

    public OrderDetails(){}

    public OrderDetails(List<LineItem> lineItems, long restaurantId, long consumerId) {
        this.lineItems = lineItems;
        this.restaurantId = restaurantId;
        this.consumerId = consumerId;
    }

    //getter and setter

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(long consumerId) {
        this.consumerId = consumerId;
    }
}
