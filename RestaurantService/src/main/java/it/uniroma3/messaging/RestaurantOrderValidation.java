package it.uniroma3.messaging;

import it.uniroma3.OrderServiceChannel;
import it.uniroma3.common.event.DomainEvent;
import it.uniroma3.domain.RestaurantService;
import it.uniroma3.event.OrderCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RestaurantOrderValidation {

    @Autowired
    private RestaurantService restaurantService;

    @KafkaListener(topics = OrderServiceChannel.orderServiceChannel)
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception{
        System.out.println("######### RESTAURANT IN ASCOLTO  ########");
        DomainEvent event = evt.value();
        OrderCreatedEvent orderCreatedEvent = (OrderCreatedEvent) event;
        restaurantService.validateOrderRestaurant(orderCreatedEvent.getOrderId(), orderCreatedEvent.getRestaurantId());
    }


}
