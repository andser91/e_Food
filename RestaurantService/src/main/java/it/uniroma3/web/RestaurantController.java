package it.uniroma3.web;

import io.micrometer.core.instrument.MeterRegistry;
import it.uniroma3.domain.IRestaurantService;
import it.uniroma3.domain.MenuItem;
import it.uniroma3.domain.Restaurant;
import it.uniroma3.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path="/restaurants")
public class RestaurantController {
    @Autowired
    private IRestaurantService restaurantService;

    @Autowired
    MeterRegistry meterRegistry;

    @Value("${version}")
    private String version;

    @GetMapping("/prova")
    public ResponseEntity<String> prova(){
        return new ResponseEntity<>("Versione: " + version , HttpStatus.OK);
    }

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
        Restaurant restaurant = restaurantService.create(request.getName(), request.getCity());
        return makeCreateRestaurantResponse(restaurant);
    }

    /* Crea la risposta a partire dal ristorante. */
    private CreateRestaurantResponse makeCreateRestaurantResponse(Restaurant restaurant) {
        return new CreateRestaurantResponse(restaurant.getId());
    }

    /** Crea un nuovo menu per il ristorante con restaurantId. */
    @RequestMapping(path="/{restaurantId}/menu", method=RequestMethod.POST)
    public CreateRestaurantMenuResponse createRestaurantMenu(@PathVariable Long restaurantId, @RequestBody CreateRestaurantMenuRequest request) {
        List<MenuItem> menuItems = getMenuItems(request);
        Restaurant restaurant = restaurantService.createMenu(restaurantId, menuItems);
        return makeCreateRestaurantMenuResponse(restaurant);
    }

    /* Estrae dalla richiesta la lista degli item. */
    private List<MenuItem> getMenuItems(CreateRestaurantMenuRequest request) {
        return request.getMenuItems()
                .stream()
                .map(x -> new MenuItem(x.getItemId(), x.getName(), x.getPrice()))
                .collect(Collectors.toList());
    }

    /* Crea la risposta a partire dal ristorante. */
    private CreateRestaurantMenuResponse makeCreateRestaurantMenuResponse(Restaurant restaurant) {
        return new CreateRestaurantMenuResponse(restaurant.getId());
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
        return new GetRestaurantResponse(restaurant.getId(), restaurant.getCity(), restaurant.getName());
    }

    /** Trova il menu del ristorante con restaurantId. */
    @RequestMapping(path="/{restaurantId}/menu", method=RequestMethod.GET)
    public ResponseEntity<GetRestaurantMenuResponse> getRestaurantMenu(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        if (restaurant!=null) {
            meterRegistry.counter("restaurantMenu.request","version", version).increment();
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("version",
                    version);
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(makeGetRestaurantMenuResponse(restaurant));

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /* Crea la risposta a partire dal ristorante. */
    private GetRestaurantMenuResponse makeGetRestaurantMenuResponse(Restaurant restaurant) {
        List<RestaurantMenuItem> menuItems =
                restaurant.getMenu().getMenuItems()
                        .stream()
                        .map(x -> new RestaurantMenuItem(x.getId(), x.getName(), x.getPrice()))
                        .collect(Collectors.toList());
        return new GetRestaurantMenuResponse(menuItems);
    }


    /** Cancella un ristorante per id **/
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteById(id);
    }
}
