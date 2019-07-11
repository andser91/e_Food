package it.uniroma3.messaging;

import io.micrometer.core.instrument.MeterRegistry;
import it.uniroma3.ConsumerServiceChannel;
import it.uniroma3.KitchenServiceChannel;
import it.uniroma3.common.event.DomainEvent;
import it.uniroma3.domain.OrderService;
import it.uniroma3.event.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@EnableKafka
public class KitchenAndConsumerEventListener {

    private Logger logger = Logger.getLogger("KitchenAndConsumerEventListener");

    @Autowired
    private OrderService orderService;
    @Autowired
    private MeterRegistry meterRegistry;

    @KafkaListener(topics = {KitchenServiceChannel.kitchenServiceChannel, ConsumerServiceChannel.consumerServiceChannel})
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception {
        logger.info("ORDER DOMAIN EVENT CONSUMER: " + evt.toString());

        DomainEvent event = evt.value();
        System.out.println(event.getClass());
        if (event.getClass().equals(OrderConsumerValidatedEvent.class)) {
            OrderConsumerValidatedEvent domainEvent = (OrderConsumerValidatedEvent) event;
            orderService.confirmConsumer(domainEvent.getOrderId(), domainEvent.getConsumerId());
        } else if (event.getClass().equals(OrderConsumerInvalidatedEvent.class)) {
            OrderConsumerInvalidatedEvent domainEvent = (OrderConsumerInvalidatedEvent) event;
            orderService.invalidateConsumer(domainEvent.getOrderId(), domainEvent.getConsumerId());
            meterRegistry.counter("order.count","status","invalid-consumer").increment();
        }else if(event.getClass().equals(TicketValidEvent.class)) {
            System.out.println("############CONFERMATO");
            TicketValidEvent domainEvent = (TicketValidEvent) event;
            orderService.confirmTicket(domainEvent.getOrderId(), domainEvent.getTicketId());
            meterRegistry.counter("order.count", "status","confirmed").increment();
        }else if(event.getClass().equals(TicketInvalidEvent.class)) {
            TicketInvalidEvent domainEvent = (TicketInvalidEvent) event;
            orderService.invalidateTicket(domainEvent.getOrderId(), domainEvent.getTicketId());
            meterRegistry.counter("order.count", "status","invalid-ticket").increment();
        }else if(event.getClass().equals(TicketCreatedEvent.class)) {
            TicketCreatedEvent domainEvent = (TicketCreatedEvent) event;
            orderService.attendingConfirmTicket(domainEvent.getOrderId(), domainEvent.getTicketId());
        }
    }
}
