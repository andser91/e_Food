package it.uniroma3.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestaurantService implements IRestaurantService{

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> findAll(){
        return (List<Restaurant>)restaurantRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.restaurantRepository.deleteById(id);
    }

    @Override
    public Restaurant findById(Long id){
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public Restaurant create(String name, String address) {
        Restaurant restaurant = Restaurant.create(name, address);
        restaurant = restaurantRepository.save(restaurant);
        return restaurant;
    }
}
