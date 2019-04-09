package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.Data;

import java.util.Objects;
@Data
public class TicketValidEvent implements DomainEvent{
    private Long ticketId;
    private Long orderId;

    public TicketValidEvent(){}

    public TicketValidEvent(Long ticketId, Long orderId) {
        this.ticketId = ticketId;
        this.orderId = orderId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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


