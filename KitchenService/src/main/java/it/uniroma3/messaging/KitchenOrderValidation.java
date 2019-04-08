package it.uniroma3.messaging;

import it.uniroma3.OrderServiceChannel;
import it.uniroma3.common.event.DomainEvent;
import it.uniroma3.domain.KitchenService;
import it.uniroma3.event.OrderCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KitchenOrderValidation {
    @Autowired
    private KitchenService kitchenService;

    @KafkaListener(topics = OrderServiceChannel.orderServiceChannel)
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception{
        System.out.println("######### KITCHEN IN ASCOLTO  ########");
        DomainEvent event = evt.value();
        OrderCreatedEvent orderCreatedEvent = (OrderCreatedEvent) event;
        kitchenService.validateOrder(orderCreatedEvent.getOrderId(), orderCreatedEvent.getKitchenId(), orderCreatedEvent.getRestaurantId());
    }

}
