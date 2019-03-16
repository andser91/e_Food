package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.Data;

@Data
public class OrderRestaurantValidatedEvent implements DomainEvent {

    private Long orderId;
    private Long restaurantId;

    public OrderRestaurantValidatedEvent() {    }

    public OrderRestaurantValidatedEvent(Long orderId, Long restaurantId) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "OrderRestaurantValidateEvent{"+
                "orderId='" + orderId +"'"+
                "restaurantId='"+ restaurantId+
                "}";
    }
}
