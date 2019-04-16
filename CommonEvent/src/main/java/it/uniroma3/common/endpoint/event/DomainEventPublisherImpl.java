package it.uniroma3.common.endpoint.event;

import it.uniroma3.common.event.DomainEvent;
import it.uniroma3.common.event.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class
DomainEventPublisherImpl implements DomainEventPublisher {

    @Autowired
    private KafkaTemplate<String, DomainEvent> template;


    @Override
    public void publish(DomainEvent event, String channel) {
        template.send(channel, event);
        System.out.println("##### INVIATO MESSAGGIO ######");
    }
}
