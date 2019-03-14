package it.uniroma3.common.event;

public interface DomainEventPublisher {

    void subscribe(DomainEventListener eventListener);

    void publish(DomainEvent event, String channel);
}
