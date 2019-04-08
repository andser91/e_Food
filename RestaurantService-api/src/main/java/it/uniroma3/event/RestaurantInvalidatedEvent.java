package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.Data;

@Data
public class RestaurantInvalidatedEvent implements DomainEvent {
    private Long ticketId;
    private Long restaurantId;

    public RestaurantInvalidatedEvent() {    }

    public RestaurantInvalidatedEvent(Long ticketId, Long restaurantId) {
        this.ticketId = ticketId;
        this.restaurantId = restaurantId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "RestaurantInvalidatedEvent{"+
                "ticketId='"+ ticketId +"'"+
                "restaurantId='"+restaurantId+"'"+
                "}";
    }
}

