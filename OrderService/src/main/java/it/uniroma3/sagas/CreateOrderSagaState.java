package it.uniroma3.sagas;

import it.uniroma3.event.OrderDetails;

public class CreateOrderSagaState {

    private Long orderId;
    private OrderDetails orderDetails;

    public CreateOrderSagaState(){}

    public CreateOrderSagaState(Long orderId, OrderDetails orderDetails) {
        this.orderId = orderId;
        this.orderDetails = orderDetails;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }
}
