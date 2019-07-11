package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent implements DomainEvent {

    private Long id;
    private String firstname;
    private String lastname;
}
