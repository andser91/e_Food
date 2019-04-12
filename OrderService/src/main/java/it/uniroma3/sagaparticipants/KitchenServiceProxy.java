package it.uniroma3.sagaparticipants;

import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;
import it.uniroma3.*;

public class KitchenServiceProxy {
    public final CommandEndpoint<CreateTicket> create= CommandEndpointBuilder
            .forCommand(CreateTicket.class)
            .withChannel(KitchenServiceChannel.kitchenServiceChannel)
            .withReply(CreateTicketReply.class)
            .build();

    public final CommandEndpoint<ConfirmCreateTicket> confirmCreate = CommandEndpointBuilder
            .forCommand(ConfirmCreateTicket.class)
            .withChannel(KitchenServiceChannel.kitchenServiceChannel)
            .withReply(Success.class)
            .build();
    public final CommandEndpoint<CancelTicket> cancel = CommandEndpointBuilder
            .forCommand(CancelTicket.class)
            .withChannel(KitchenServiceChannel.kitchenServiceChannel)
            .withReply(Success.class)
            .build();
}
