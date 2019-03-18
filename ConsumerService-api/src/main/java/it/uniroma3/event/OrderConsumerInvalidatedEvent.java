package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;

public class OrderConsumerInvalidatedEvent implements DomainEvent {
    private Long orderId;
    private Long consumerId;

    public OrderConsumerInvalidatedEvent(Long orderId, Long consumerId) {
        this.orderId = orderId;
        this.consumerId = consumerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }
}
