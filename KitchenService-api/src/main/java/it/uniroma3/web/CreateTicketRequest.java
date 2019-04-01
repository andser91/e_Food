package it.uniroma3.web;

public class CreateTicketRequest {

    private Long restaurantId;

    public CreateTicketRequest(){}

    public CreateTicketRequest(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
