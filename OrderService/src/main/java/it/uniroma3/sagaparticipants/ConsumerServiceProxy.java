package it.uniroma3.sagaparticipants;

import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;
import it.uniroma3.ConsumerServiceChannel;
import it.uniroma3.ValidateOrderByConsumer;

public class ConsumerServiceProxy {
    public final CommandEndpoint<ValidateOrderByConsumer> validateOrder= CommandEndpointBuilder
            .forCommand(ValidateOrderByConsumer.class)
            .withChannel(ConsumerServiceChannel.consumerServiceChannel)
            .withReply(Success.class)
            .build();
}
