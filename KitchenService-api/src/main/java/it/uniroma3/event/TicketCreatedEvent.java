package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.Data;

import java.util.Objects;

@Data
public class TicketCreatedEvent implements DomainEvent {
    private Long orderId;
    private Long ticketId;
    private Long restaurantId;

    public TicketCreatedEvent(){}

    public TicketCreatedEvent(Long orderId, Long ticketId, Long restaurantId) {
        this.orderId = orderId;
        this.ticketId = ticketId;
        this.restaurantId = restaurantId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketCreatedEvent that = (TicketCreatedEvent) o;
        return ticketId.equals(that.ticketId) &&
                restaurantId.equals(that.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, restaurantId, orderId);
    }

    @Override
    public String toString() {
        return "TicketCreatedEvent{" +
                "orderId=" + orderId +
                ", ticketId=" + ticketId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
