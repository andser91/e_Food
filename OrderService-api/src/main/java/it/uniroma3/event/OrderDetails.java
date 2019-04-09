package it.uniroma3.event;

import java.util.List;

public class OrderDetails {

    private List<LineItem> lineItems;
    private Long restaurantId;
    private Long consumerId;

    //constructs

    public OrderDetails(){}

    public OrderDetails(List<LineItem> lineItems, Long restaurantId, Long consumerId) {
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

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }
}
