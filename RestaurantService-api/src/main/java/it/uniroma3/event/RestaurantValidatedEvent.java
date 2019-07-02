package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantValidatedEvent implements DomainEvent {

    private Long ticketId;
    private Long restaurantId;

}
