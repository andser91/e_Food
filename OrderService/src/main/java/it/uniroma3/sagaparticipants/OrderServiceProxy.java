package it.uniroma3.sagaparticipants;

import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;
import it.uniroma3.*;
import it.uniroma3.domain.OrderService;

public class OrderServiceProxy {

    public final CommandEndpoint<RejectOrderCommand> reject= CommandEndpointBuilder
            .forCommand(RejectOrderCommand.class)
            .withChannel(OrderServiceChannel.orderServiceChannel)
            .withReply(Success.class)
            .build();
    public final CommandEndpoint<ApproveOrderCommand> approve = CommandEndpointBuilder
            .forCommand(ApproveOrderCommand.class)
            .withChannel(OrderServiceChannel.orderServiceChannel)
            .withReply(Success.class)
            .build();

}
