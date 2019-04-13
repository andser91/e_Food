package it.uniroma3.sagas;

import it.uniroma3.*;
import it.uniroma3.event.OrderDetails;

public class CreateOrderSagaState {

    private Long orderId;
    private OrderDetails orderDetails;
    private Long ticketId;

    public CreateOrderSagaState(){}

    public CreateOrderSagaState(Long orderId, OrderDetails orderDetails) {
        this.orderId = orderId;
        this.orderDetails = orderDetails;
    }

    //commands
    ValidateOrderByConsumer makeValdateOrderByConsumerCommand(){
        return new ValidateOrderByConsumer(orderDetails.getConsumerId(),orderId);
    }
    CreateTicket makeCreateTicketCommand(){
        return new CreateTicket(orderDetails.getRestaurantId(), orderId);
    }
    ConfirmCreateTicket makeConfirmCreateTicketCommand(){
        return new ConfirmCreateTicket(orderId, ticketId);
    }
    void handleCreateTicketReply(CreateTicketReply reply){
        setTicketId(reply.getTicketId());

    }
    RejectOrderCommand makeRejectOrderCommand(){
        return new RejectOrderCommand(orderId);
    }
    CancelTicket makeCancelTicketCommand(){
        return new CancelTicket(ticketId);
    }
    ApproveOrderCommand makeApproveOrderCommand(){
        return new ApproveOrderCommand(orderId);
    }

    //getter and setter
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

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
}
