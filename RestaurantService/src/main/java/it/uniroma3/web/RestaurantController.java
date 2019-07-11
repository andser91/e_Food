package it.uniroma3.web;

import io.micrometer.core.instrument.MeterRegistry;
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
@RequestMapping(path="/restaurants")
public class RestaurantController {
    @Autowired
    private IRestaurantService restaurantService;


    @Autowired
    private MeterRegistry meterRegistry;

    /** Trova tutti i ristoranti **/
    @GetMapping("/")
    public ResponseEntity<GetRestaurantsResponse> findAll(){
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

    /** Crea un nuovo ristorante **/
    @PostMapping("/")
    public CreateRestaurantResponse createRestaurant(@RequestBody CreateRestaurantRequest request) {
        Restaurant restaurant = restaurantService.create(request.getName(), request.getAddress());
        meterRegistry.counter("restaurant.count").increment();
        return makeCreateRestaurantResponse(restaurant);
    }

    /* Crea la risposta a partire dal ristorante. */
    private CreateRestaurantResponse makeCreateRestaurantResponse(Restaurant restaurant) {
        return new CreateRestaurantResponse(restaurant.getId());
    }


    /** Trova un ristorante per id **/
    @GetMapping("/{id}")
    public ResponseEntity<GetRestaurantResponse> findById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.findById(id);
        if (restaurant != null){
            return new ResponseEntity<GetRestaurantResponse>(makeGetRestaurantResponse(restaurant), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private GetRestaurantResponse makeGetRestaurantResponse(Restaurant restaurant) {
        return new GetRestaurantResponse(restaurant.getId(), restaurant.getAddress(), restaurant.getName());
    }

    /** Cancella un ristorante per id **/
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteById(id);
    }
}
