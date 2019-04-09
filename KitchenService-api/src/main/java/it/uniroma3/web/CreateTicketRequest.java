package it.uniroma3.web;

public class CreateTicketRequest {

    private Long restaurantId;
    private Long orderId;

    public CreateTicketRequest(){}

    public CreateTicketRequest(Long restaurantId, Long orderId) {
        this.restaurantId = restaurantId;
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
