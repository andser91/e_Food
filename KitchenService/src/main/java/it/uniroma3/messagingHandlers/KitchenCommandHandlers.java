package it.uniroma3.messagingHandlers;

import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import it.uniroma3.CancelTicket;
import it.uniroma3.ConfirmCreateTicket;
import it.uniroma3.CreateTicket;
import it.uniroma3.CreateTicketReply;
import it.uniroma3.domain.KitchenService;
import it.uniroma3.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;
import static io.eventuate.tram.sagas.participant.SagaReplyMessageBuilder.withLock;

public class KitchenCommandHandlers {
    @Autowired
    private KitchenService kitchenService;

    public Message createTicket(CommandMessage<CreateTicket> cm){
        Ticket ticket = kitchenService.createTicket(cm.getCommand().getOrderId(), cm.getCommand().getRestaurantId());
        //viene creato l'evento di risposta per ottenere il ticketId
        CreateTicketReply reply = new CreateTicketReply(ticket.getId());
        return withLock(Ticket.class, ticket.getId()).withSuccess(reply);
    }
    private Message confirmCreateTicket
            (CommandMessage<ConfirmCreateTicket> cm) {
        Long ticketId = cm.getCommand().getTicketId();
        kitchenService.confirmCreateTicket(ticketId);
        return withSuccess();
    }

    private Message cancelCreateTicket
            (CommandMessage<CancelTicket> cm) {
        Long ticketId = cm.getCommand().getTicketId();
        kitchenService.cancelCreateTicket(ticketId);
        return withSuccess();
    }

}
