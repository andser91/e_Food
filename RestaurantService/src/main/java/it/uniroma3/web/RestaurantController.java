package it.uniroma3.web;

import it.uniroma3.domain.IRestaurantService;
import it.uniroma3.domain.Restaurant;
import it.uniroma3.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;


@RestController
public class RestaurantController {
    @Autowired
    private IRestaurantService restaurantService;

    //Aggregate items
    //Restituisce tutti i ristoranti
    @GetMapping("/restaurants")
    ResponseEntity<GetRestaurantsResponse> findAll(){
        List<Restaurant> restaurants = restaurantService.findAll();
        if (restaurants != null) {
            return new ResponseEntity<GetRestaurantsResponse>(makeGetRestaurantsResponse(restaurants), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private GetRestaurantsResponse makeGetRestaurantsResponse(List<Restaurant> restaurants) {
        List<GetRestaurantResponse> responses = restaurants.stream().map(restaurant -> makeGetRestaurantResponse(restaurant)).collect(Collectors.toList());
        return new GetRestaurantsResponse(responses);
    }

    //crea un nuovo ristorante nel database
    @PostMapping("/restaurants")
    CreateRestaurantResponse newRestaurant(@RequestBody CreateRestaurantRequest request) {
        Restaurant restaurant = restaurantService.create(request.getName(), request.getAddress());
        return makeCreateRestaurantResponse(restaurant);
    }

    private CreateRestaurantResponse makeCreateRestaurantResponse(Restaurant restaurant) {
        return new CreateRestaurantResponse(restaurant.getId());
    }

    //restituisce  un ristorante dato l'id
    @GetMapping("/restaurants/{id}")
    ResponseEntity<GetRestaurantResponse> findById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.findById(id).get();
        if (restaurant != null){
            return new ResponseEntity<GetRestaurantResponse>(makeGetRestaurantResponse(restaurant), HttpStatus.OK);
        }
        else {
            throw new RestaurantNotFoundException(id);
        }
    }

    private GetRestaurantResponse makeGetRestaurantResponse(Restaurant restaurant) {
        return new GetRestaurantResponse(restaurant.getId(), restaurant.getAddress(), restaurant.getName());
    }

    //cancellazione di un ristorante per id
    @DeleteMapping("/restaurants/{id}")
    void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteById(id);
    }
}
