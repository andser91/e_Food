package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreatedEvent implements DomainEvent {
    private Long orderId;
    private Long ticketId;
    private Long restaurantId;
}
