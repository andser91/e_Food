package it.uniroma3.common.endpoint.event;

import it.uniroma3.common.event.DomainEvent;
import it.uniroma3.common.event.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
@Transactional
public class DomainEventPublisherImpl implements DomainEventPublisher {
    private Logger logger = Logger.getLogger("DomainEventPublisherImpl");

    @Autowired
    private KafkaTemplate<String, DomainEvent> template;


    @Override
    public void publish(DomainEvent event, String channel) {
        logger.info("PUBLISHING DOMAIN EVENT: " + event.toString() + " ON CHANNEL: " + channel);
        template.send(channel, event);
    }
}
