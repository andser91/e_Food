package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.Data;


@Data
public class RestaurantCreatedEvent implements DomainEvent {

    private Long restaurantId;
    private String restaurantName;
    private String restaurantAddress;

    public RestaurantCreatedEvent() { }

    public RestaurantCreatedEvent(Long restaurantId, String restaurantName, String restaurantAddress) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
    }

    @Override
    public String toString() {
        return "RestaurantCreatedEvent{"+
                "restaurantId='" +restaurantId+"'" +
                "restaurantName='"+restaurantName+"'"+
                "restaurantAddress='"+restaurantAddress+"'"+
                "}";
    }
}
