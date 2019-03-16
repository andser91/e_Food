package it.uniroma3.domain;

import it.uniroma3.RestaurantServiceChannel;
import it.uniroma3.common.event.DomainEventPublisher;
import it.uniroma3.event.OrderRestaurantInvalidateEvent;
import it.uniroma3.event.OrderRestaurantValidatedEvent;
import it.uniroma3.event.RestaurantCreatedEvent;
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
    @Autowired
    private DomainEventPublisher domainEventPublisher;

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
        RestaurantCreatedEvent event = makeRestaurantCreatedEvent(restaurant);
        domainEventPublisher.publish(event, RestaurantServiceChannel.restaurantServiceChannel);
        return restaurant;
    }

    private RestaurantCreatedEvent makeRestaurantCreatedEvent(Restaurant restaurant) {
        return new RestaurantCreatedEvent(restaurant.getId(), restaurant.getName(), restaurant.getAddress());
    }

    @Override
    public void validateOrderRestaurant(Long orderId, Long restaurantId) {
        Restaurant restaurant = findById(restaurantId);
        if (restaurant != null){
            OrderRestaurantValidatedEvent event = new OrderRestaurantValidatedEvent(orderId, restaurantId);
            domainEventPublisher.publish(event, RestaurantServiceChannel.restaurantServiceChannel);
        }
        else {
            OrderRestaurantInvalidateEvent event = new OrderRestaurantInvalidateEvent(orderId, restaurantId);
            domainEventPublisher.publish(event, RestaurantServiceChannel.restaurantServiceChannel);
        }
    }




}
