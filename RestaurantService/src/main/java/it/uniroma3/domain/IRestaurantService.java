package it.uniroma3.domain;

import org.apache.kafka.common.protocol.types.Field;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IRestaurantService {

    List<Restaurant> findAll();
    Collection<Restaurant> findAllByCity(String city);
    void deleteById(Long id);
    Restaurant findById(Long id);
    Restaurant create (String name, String city);
    void validateTicketRestaurant(Long orderId, Long restaurantId);
    Restaurant createMenu(Long restaurantId, List<MenuItem> menuItems);
}
