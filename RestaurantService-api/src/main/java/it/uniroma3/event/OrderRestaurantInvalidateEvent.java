package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.Data;

@Data
public class OrderRestaurantInvalidateEvent implements DomainEvent {
    private Long orderId;
    private Long restaurantId;

    public OrderRestaurantInvalidateEvent() {    }

    public OrderRestaurantInvalidateEvent(Long orderId, Long restaurantId) {
        this.orderId = orderId;
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

