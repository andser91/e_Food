package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;

public class RestaurantValidatedEvent implements DomainEvent {

    private Long ticketId;
    private Long restaurantId;

    public RestaurantValidatedEvent() {    }

    public RestaurantValidatedEvent(Long ticketId, Long restaurantId) {
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
        return "RestaurantValidatedEvent{"+
                "ticketId='" + ticketId +"'"+
                "restaurantId='"+ restaurantId+
                "}";
    }
}
