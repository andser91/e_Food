package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.Data;

@Data
public class OrderRestaurantInvalidatedEvent implements DomainEvent {
    private Long orderId;
    private Long restaurantId;

    public OrderRestaurantInvalidatedEvent() {    }

    public OrderRestaurantInvalidatedEvent(Long orderId, Long restaurantId) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "OrderRestaurantInvalidateEvent{"+
                "orderId='"+orderId+"'"+
                "restaurantId='"+restaurantId+"'"+
                "}";
    }
}

