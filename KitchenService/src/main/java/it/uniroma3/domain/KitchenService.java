package it.uniroma3.domain;

import it.uniroma3.KitchenServiceChannel;
import it.uniroma3.event.TicketCreatedEvent;
import it.uniroma3.common.event.DomainEventPublisher;
import it.uniroma3.event.TicketInvalidEvent;
import it.uniroma3.event.TicketValidEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class KitchenService implements IKitchenService{

    @Autowired
    KitchenRepository kitchenRepository;
    @Autowired
    DomainEventPublisher domainEventPublisher;

    @Override
    public List<Ticket> findAll() {
        return (List<Ticket>) kitchenRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        kitchenRepository.deleteById(id);
    }

    @Override
    public Ticket findById(Long id) {
        return kitchenRepository.findById(id).orElse(null);
    }

    @Override
    public Ticket create(Long restaurantId) {
       // return createAsincrona(restaurantId);
        return createSincrona(restaurantId);
    }

    private Ticket createAsincrona(Long restaurantId){
        Ticket ticket = Ticket.create(restaurantId);
        ticket = kitchenRepository.save(ticket);
        TicketCreatedEvent event = makeTicketCreatedEvent(ticket);

        domainEventPublisher.publish(event, KitchenServiceChannel.kitchenServiceChannel);
        return ticket;
    }

    private TicketCreatedEvent makeTicketCreatedEvent(Ticket ticket){
        return new TicketCreatedEvent(ticket.getId(), ticket.getRestaurantId());
    }

    private Ticket createSincrona(Long restaurantId){
      Ticket ticket = Ticket.create(restaurantId);
      return kitchenRepository.save(ticket);
    }

    @Override
    public Ticket acceptTicket(Long ticketId){
        Ticket ticket = findById(ticketId);
        if(ticket.getState().equals(TicketState.RESTAURANT_APPROVED)){
            ticket.setState(TicketState.APPROVED);
            ticket = kitchenRepository.save(ticket);
            //crea evento di validazione del ticket
            TicketValidEvent ticketValidEvent = makeTicketValidEvent(ticket);
            //pubblica evento di validazione del ticket
            domainEventPublisher.publish(ticketValidEvent, KitchenServiceChannel.kitchenServiceChannel);
        }
        return ticket;
    }
    private TicketValidEvent makeTicketValidEvent(Ticket ticket){
        return new TicketValidEvent(ticket.getId(), ticket.getRestaurantId());
    }
    public Ticket confirmRestaurant(Long ticketId){
            Ticket ticket = findById(ticketId);
            ticket.setState(TicketState.RESTAURANT_APPROVED);
            ticket = kitchenRepository.save(ticket);
            return ticket;

    }

    public Ticket refuseTicket(Long ticketId){
        Ticket ticket = findById(ticketId);
        ticket.setState(TicketState.DISAPPROVED);
        kitchenRepository.save(ticket);
        //crea evento di non validazione del ticket
        TicketInvalidEvent ticketInvalidEvent = makeTicketInvalidEvent(ticket);
        //pubblica evento di non validazione del ticket
        domainEventPublisher.publish(ticketInvalidEvent, KitchenServiceChannel.kitchenServiceChannel );
        return ticket;
    }
    private TicketInvalidEvent makeTicketInvalidEvent(Ticket ticket){
        return new TicketInvalidEvent(ticket.getId(), ticket.getRestaurantId());
    }
    public void validateOrder(Long orderId, Long kitchenId, Long restaurantId){
        //creo una nuova comanda
        Ticket ticket = create(restaurantId);
        //salvo la comanda nel db in stato pending
        kitchenRepository.save(ticket);
        //creo l'evento di creazione della comanda che riceveranno restaurant e order
        TicketCreatedEvent ticketCreatedEvent = makeTicketCreatedEvent(ticket);
        System.out.println("### INVIATO EVENTO KITCHEN CREATED ###");
        //pubblica evento di creazione sul canale di kitchen
        domainEventPublisher.publish(ticketCreatedEvent, KitchenServiceChannel.kitchenServiceChannel);
    }
}
