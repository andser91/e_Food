package it.uniroma3.domain;

import java.util.List;
import java.util.Optional;

public interface IRestaurantService {

    List<Restaurant> findAll();
    void save(Restaurant restaurant);
    void deleteById(Long id);
    Optional<Restaurant> findById(Long id);
    Restaurant create (String name, String address);
}
