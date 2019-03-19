package it.uniroma3.messaging;

import it.uniroma3.ConsumerServiceChannel;
import it.uniroma3.RestaurantServiceChannel;
import it.uniroma3.common.event.DomainEvent;
import it.uniroma3.domain.OrderService;
import it.uniroma3.event.OrderConsumerInvalidatedEvent;
import it.uniroma3.event.OrderConsumerValidatedEvent;
import it.uniroma3.event.OrderRestaurantInvalidatedEvent;
import it.uniroma3.event.OrderRestaurantValidatedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
public class RestaurantAndConsumerEventListener {

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = {RestaurantServiceChannel.restaurantServiceChannel, ConsumerServiceChannel.consumerServiceChannel})
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception {
        DomainEvent event = evt.value();
        if (event.getClass().equals(OrderRestaurantValidatedEvent.class)) {
            OrderRestaurantValidatedEvent domainEvent = (OrderRestaurantValidatedEvent) event;
            orderService.confirmRestaurant(domainEvent.getOrderId(), domainEvent.getRestaurantId());
        } else if (event.getClass().equals(OrderRestaurantInvalidatedEvent.class)) {
            OrderRestaurantInvalidatedEvent domainEvent = (OrderRestaurantInvalidatedEvent) event;
            orderService.invalidateRestaurant(domainEvent.getOrderId(), domainEvent.getRestaurantId());
            }else if (event.getClass().equals(OrderConsumerValidatedEvent.class)) {
                OrderConsumerValidatedEvent domainEvent = (OrderConsumerValidatedEvent) event;
                orderService.confirmConsumer(domainEvent.getOrderId(), domainEvent.getConsumerId());
                } else if (event.getClass().equals(OrderConsumerInvalidatedEvent.class)) {
                    OrderConsumerInvalidatedEvent domainEvent = (OrderConsumerInvalidatedEvent) event;
                    orderService.invalidateConsumer(domainEvent.getOrderId(), domainEvent.getConsumerId());
                    }
            }
}
