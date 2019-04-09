package it.uniroma3.web;


import java.util.List;

public class GetOrderResponse {

    private Long orderId;
    private Long consumerId;
    private Long restaurantId;
    private Long ticketId;
    private List<LineItem> lineItems;
    private String orderState;


    public GetOrderResponse() {
    }

    public GetOrderResponse(Long orderId, Long consumerId, Long restaurantId, Long ticketId, List<LineItem> lineItems, String orderState) {
        this.orderId = orderId;
        this.consumerId = consumerId;
        this.restaurantId = restaurantId;
        this.ticketId = ticketId;
        this.lineItems = lineItems;
        this.orderState = orderState;
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

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
