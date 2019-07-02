package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderConsumerValidatedEvent implements DomainEvent {
    private Long orderId;
    private Long consumerId;
}
