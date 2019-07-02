package it.uniroma3.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private TicketState state;
    private Long restaurantId;

    public Ticket(Long restaurantId, Long orderId) {
        super();
        this.state = TicketState.PENDING;
        this.restaurantId = restaurantId;
        this.orderId = orderId;
    }

    public static Ticket create(Long restaurantId, Long orderId){
        return new Ticket(restaurantId, orderId);
    }
}
