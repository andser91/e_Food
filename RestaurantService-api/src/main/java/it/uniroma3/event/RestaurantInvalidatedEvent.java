package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RestaurantInvalidatedEvent implements DomainEvent {
    private Long ticketId;
    private Long restaurantId;

}

