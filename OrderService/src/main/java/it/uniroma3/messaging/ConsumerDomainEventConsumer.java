package it.uniroma3.messaging;

import it.uniroma3.domain.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerDomainEventConsumer {

    @Autowired
    OrderService orderService;


    public void listen(){

    }
}
