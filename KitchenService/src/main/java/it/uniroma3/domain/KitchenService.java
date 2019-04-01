package it.uniroma3.domain;

import it.uniroma3.KitchenServiceChannel;
import it.uniroma3.event.TicketCreatedEvent;
import it.uniroma3.common.event.DomainEventPublisher;
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
        ticket.setState(TicketState.APPROVED);
        ticket = kitchenRepository.save(ticket);
        return ticket;
    }

    public Ticket refuseTicket(Long ticketId){
        Ticket ticket = findById(ticketId);
        ticket.setState(TicketState.DISAPPROVED);
        kitchenRepository.save(ticket);
        return ticket;
    }
}
