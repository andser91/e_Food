package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import java.util.List;

public class OrderCreatedEvent implements DomainEvent {
    private Long orderId;
    private Long consumerId;
    private Long restaurantId;
    private List<LineItem> lineItems;


    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(Long orderId, Long consumerId, Long restaurantId, List<LineItem> lineItems) {
        this.orderId = orderId;
        this.consumerId = consumerId;
        this.restaurantId = restaurantId;
        this.lineItems = lineItems;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "orderId=" + orderId +
                ", consumerId=" + consumerId +
                ", restaurantId=" + restaurantId +
                ", lineItems=" + lineItems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderCreatedEvent that = (OrderCreatedEvent) o;
        return orderId.equals(that.orderId) &&
                consumerId.equals(that.consumerId) &&
                restaurantId.equals(that.restaurantId);
    }

    @Override
    public int hashCode() {
        return orderId.hashCode() + consumerId.hashCode() + restaurantId.hashCode();
    }
}
