package it.uniroma3.domain;

import java.util.List;
import java.util.Optional;

public interface IRestaurantService {

    List<Restaurant> findAll();
    void deleteById(Long id);
    Restaurant findById(Long id);
    Restaurant create (String name, String address);
    void validateOrderRestaurant(Long orderId, Long restaurantId);
}
