package it.uniroma3.event;

import it.uniroma3.common.event.DomainEvent;
import lombok.Data;

import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantCreatedEvent that = (RestaurantCreatedEvent) o;
        return Objects.equals(restaurantId, that.restaurantId) &&
                Objects.equals(restaurantName, that.restaurantName);
    }

    @Override
    public int hashCode() {
        return restaurantId.hashCode() + restaurantName.hashCode();
    }
}
