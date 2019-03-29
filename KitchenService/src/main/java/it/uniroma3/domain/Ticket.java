package it.uniroma3.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private TicketState state;
    private Long restaurantId;

    public Ticket(){super();}

    public Ticket(Long restaurantId) {
        super();
        this.state = TicketState.PENDING;
        this.restaurantId = restaurantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketState getState() {
        return state;
    }

    public void setState(TicketState state) {
        this.state = state;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public static Ticket create(Long restaurantId){
        return new Ticket(restaurantId);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id.equals(ticket.id) &&
                state == ticket.state &&
                restaurantId.equals(ticket.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, restaurantId);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", state=" + state +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
