package it.uniroma3.messaging;

import it.uniroma3.common.event.DomainEvent;
import it.uniroma3.domain.ConsumerService;
import it.uniroma3.event.UserCreatedEvent;

import it.uniroma3.event.UserServiceChannel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@EnableKafka
public class UserChannelListener {

    @Autowired
    private ConsumerService consumerService;

    @KafkaListener(topics = UserServiceChannel.userServiceChannel)
    public void listen(ConsumerRecord<String, DomainEvent> evt) throws Exception {
        DomainEvent event = evt.value();
        UserCreatedEvent domainEvent = (UserCreatedEvent) event;
        consumerService.createWithId(domainEvent.getId(), domainEvent.getFirstname(), domainEvent.getLastname());
    }
}
