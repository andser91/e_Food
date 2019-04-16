package it.uniroma3.domain;

import it.uniroma3.RestaurantServiceChannel;
import it.uniroma3.common.event.DomainEventPublisher;
import it.uniroma3.event.RestaurantInvalidatedEvent;
import it.uniroma3.event.RestaurantValidatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        //RestaurantCreatedEvent it.uniroma3.event = makeRestaurantCreatedEvent(restaurant);
        //domainEventPublisher1.publish(it.uniroma3.event, RestaurantServiceChannel.restaurantServiceChannel);
        return restaurant;
    }

    /*
    private RestaurantCreatedEvent makeRestaurantCreatedEvent(Restaurant restaurant) {
        return new RestaurantCreatedEvent(restaurant.getId(), restaurant.getName(), restaurant.getAddress());
    } */

    @Override
    public void validateTicketRestaurant(Long ticketId, Long restaurantId) {
        Restaurant restaurant = findById(restaurantId);
        if (restaurant != null){
            RestaurantValidatedEvent event = new RestaurantValidatedEvent(ticketId, restaurantId);
            domainEventPublisher.publish(event, RestaurantServiceChannel.restaurantServiceChannel);
            System.out.println("#### INVIATO EVENTO RESTAURANT VALIDATED  ###");
        }
        else {
            RestaurantInvalidatedEvent event = new RestaurantInvalidatedEvent(ticketId, restaurantId);
            domainEventPublisher.publish(event, RestaurantServiceChannel.restaurantServiceChannel);
            System.out.println("#### INVIATO EVENTO RESTAURANT INVALIDATED  ###");
        }
    }
}
