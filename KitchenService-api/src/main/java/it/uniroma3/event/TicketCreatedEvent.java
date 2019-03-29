package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.Data;

import java.util.Objects;

@Data
public class TicketCreatedEvent implements DomainEvent {
    private Long ticketId;
    private Long restaurantId;

    public TicketCreatedEvent(){}

    public TicketCreatedEvent(Long ticketId, Long restaurantId) {
        this.ticketId = ticketId;
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
        return Objects.hash(ticketId, restaurantId);
    }

    @Override
    public String toString() {
        return "TicketCreatedEvent{" +
                "ticketId=" + ticketId +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
