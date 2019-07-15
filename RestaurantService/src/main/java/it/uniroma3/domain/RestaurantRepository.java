package it.uniroma3.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface RestaurantRepository extends CrudRepository<Restaurant,Long> {

    Collection<Restaurant> findAllByCity(String city);
}
