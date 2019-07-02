package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.*;

import java.util.Objects;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCreatedEvent implements DomainEvent {

    private Long restaurantId;
    private String restaurantName;
    private String restaurantAddress;

}
