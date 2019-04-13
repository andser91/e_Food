package it.uniroma3.messaging;

import it.uniroma3.OrderServiceChannel;
import it.uniroma3.RestaurantServiceChannel;
import it.uniroma3.common.event.DomainEvent;
import it.uniroma3.domain.KitchenService;
import it.uniroma3.event.OrderCreatedEvent;
import it.uniroma3.event.RestaurantInvalidatedEvent;
import it.uniroma3.event.RestaurantValidatedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KitchenOrderValidation {
    @Autowired
    private KitchenService kitchenService;

    @KafkaListener(topics = { OrderServiceChannel.orderServiceChannel, RestaurantServiceChannel.restaurantServiceChannel})
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception{
        System.out.println("######### KITCHEN IN ASCOLTO  ########");
        DomainEvent event = evt.value();
        if (event.getClass().equals(OrderCreatedEvent.class)) {
            OrderCreatedEvent orderCreatedEvent = (OrderCreatedEvent) event;
            kitchenService.validateTicket(orderCreatedEvent.getOrderId(), orderCreatedEvent.getRestaurantId());
        }else{ if (event.getClass().equals(RestaurantValidatedEvent.class)) {
            RestaurantValidatedEvent domainEvent = (RestaurantValidatedEvent) event;
            kitchenService.confirmRestaurant(domainEvent.getTicketId());
        } else if (event.getClass().equals(RestaurantInvalidatedEvent.class)) {
            RestaurantInvalidatedEvent domainEvent = (RestaurantInvalidatedEvent) event;
            kitchenService.refuseTicket(domainEvent.getTicketId());
        }

        }
    }

}
