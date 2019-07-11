package it.uniroma3.messaging;

import it.uniroma3.ConsumerServiceChannel;
import it.uniroma3.OrderServiceChannel;
import it.uniroma3.common.event.DomainEvent;
import it.uniroma3.domain.ConsumerService;
import it.uniroma3.event.OrderCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@EnableKafka
public class ConsumerOrderValidation {

    @Autowired
    private ConsumerService consumerService;
    private Logger logger = Logger.getLogger("ConsumerOrderValidation");

    @KafkaListener(topics = OrderServiceChannel.orderServiceChannel)
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception {
        logger.info("CONSUMER DOMAIN EVENT CONSUMER: " + evt.toString());
        DomainEvent event = evt.value();
        OrderCreatedEvent domainEvent = (OrderCreatedEvent) event;
        consumerService.validateOrder(domainEvent.getOrderId(), domainEvent.getConsumerId());
        }
    }

