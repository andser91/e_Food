package it.uniroma3.messaging;

import it.uniroma3.ConsumerServiceChannel;
import it.uniroma3.OrderServiceChannel;
import it.uniroma3.common.event.DomainEvent;
import it.uniroma3.domain.ConsumerService;
import it.uniroma3.event.OrderCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class ConsumerOrderValidation {

    @Autowired
    private ConsumerService consumerService;

    @KafkaListener(topics = OrderServiceChannel.orderServiceChannel)
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception {
        DomainEvent event = evt.value();
        OrderCreatedEvent domainEvent = (OrderCreatedEvent) event;
        consumerService.validateOrder(domainEvent.getConsumerId());
        }
    }

