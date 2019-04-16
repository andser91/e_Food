package it.uniroma3.common.event;

import org.springframework.stereotype.Service;

@Service
public interface DomainEventPublisher {

    void publish(DomainEvent event, String channel);
}
