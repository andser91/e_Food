package it.uniroma3.web;

public class CreateTicketResponse {

    private Long ticketId;
    private Long restaurantId;
    private String ticketState;

    public CreateTicketResponse(){}

    public CreateTicketResponse(Long ticketId, Long restaurantId, String ticketState) {
        this.ticketId = ticketId;
        this.restaurantId = restaurantId;
        this.ticketState = ticketState;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getTicketState() {
        return ticketState;
    }

    public void setTicketState(String ticketState) {
        this.ticketState = ticketState;
    }
}
