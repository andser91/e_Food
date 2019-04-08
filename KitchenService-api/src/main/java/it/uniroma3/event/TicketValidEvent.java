package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.Data;

import java.util.Objects;
@Data
public class TicketValidEvent implements DomainEvent{
    private Long ticketId;

    public TicketValidEvent(){}

    public TicketValidEvent(Long ticketId, Long restaurantId) {
        this.ticketId = ticketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        it.uniroma3.event.TicketValidEvent that = (it.uniroma3.event.TicketValidEvent) o;
        return ticketId.equals(that.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId);
    }

    @Override
    public String toString() {
        return "TicketCreatedEvent{" +
                "ticketId=" + ticketId +
                '}';
    }
}


