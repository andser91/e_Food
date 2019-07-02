package it.uniroma3;

import io.eventuate.tram.commands.common.Command;
import lombok.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmCreateTicket implements Command {
    private Long orderId;
    private Long ticketId;

}
