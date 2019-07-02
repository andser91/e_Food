package it.uniroma3.event;


import it.uniroma3.common.event.DomainEvent;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent implements DomainEvent {
    private Long orderId;
    private Long consumerId;
    private Long restaurantId;
    private  Long kitchenId;
    private List<LineItem> lineItems;
}
