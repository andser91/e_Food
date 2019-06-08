package it.uniroma3.messaging;

import it.uniroma3.KitchenServiceChannel;
import it.uniroma3.common.event.DomainEvent;
import it.uniroma3.domain.RestaurantService;
import it.uniroma3.event.TicketCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class RestaurantOrderValidation {

    @Autowired
    private RestaurantService restaurantService;
    private Logger logger = Logger.getLogger("RestaurantOrderValidation");

    @KafkaListener(topics = KitchenServiceChannel.kitchenServiceChannel)
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception {
        logger.info("RESTAURANT DOMAIN EVENT CONSUMER: " + evt.toString());
        DomainEvent event = evt.value();
        if (event.getClass().equals(TicketCreatedEvent.class)) {
            TicketCreatedEvent ticketCreatedEvent = (TicketCreatedEvent) event;
            restaurantService.validateTicketRestaurant(ticketCreatedEvent.getTicketId(), ticketCreatedEvent.getRestaurantId());
        }
    }

}
