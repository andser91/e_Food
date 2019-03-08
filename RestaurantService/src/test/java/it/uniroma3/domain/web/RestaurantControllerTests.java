package it.uniroma3.domain.web;

import it.uniroma3.domain.Restaurant;
import it.uniroma3.domain.RestaurantService;
import it.uniroma3.web.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RestaurantControllerTests {
    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private RestaurantService restaurantService;

    private static final Long RESTAURANT_ID = 1L;
    private static final String RESTAURANT_NAME = "r1";
    private static final String RESTAURANT_ADDRESS = "a1";

    private static final Long RESTAURANT_ID_2 = 2L;
    private static final String RESTAURANT_NAME_2 = "r2";
    private static final String RESTAURANT_ADDRESS_2 = "a2";

    private List<Restaurant> restaurants;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(RESTAURANT_NAME, RESTAURANT_ADDRESS));
        restaurants.add(new Restaurant(RESTAURANT_NAME_2, RESTAURANT_ADDRESS_2));
    }

    @Test
    public void postRestaurantTest() {
        /*verifica dell'operazione POST /restaurants */
        when (restaurantService.create(RESTAURANT_NAME, RESTAURANT_ADDRESS))
                .then(invocation -> {
                    Restaurant restaurant = new Restaurant(RESTAURANT_NAME, RESTAURANT_ADDRESS);
                    restaurant.setId(RESTAURANT_ID);
                    return restaurant;
                });

        /*invoca l'operazione POST /restaurants*/
        CreateRestaurantRequest request = new CreateRestaurantRequest();
        request.setName(RESTAURANT_NAME);
        request.setAddress(RESTAURANT_ADDRESS);
        CreateRestaurantResponse response = restaurantController.createRestaurant(request);

        /*verifica che restaurantService sia stato invocato*/
        verify(restaurantService).create(same(RESTAURANT_NAME), same(RESTAURANT_ADDRESS));

        /*verifica la risposta*/
        assertThat(response.getRestaurantId()).isEqualTo(RESTAURANT_ID);
    }

    @Test
    public void getWithTwoRestaurantsTest() {
        /*verifica dell'operazione GET /restaurants*/

        when(restaurantService.findAll())
                .then(invocation -> {
                    List<Restaurant> restaurantsResponse = new ArrayList<>(restaurants);
                    return restaurantsResponse;
                });

        ResponseEntity<GetRestaurantsResponse> response = restaurantController.findAll();

        /*verifica che il restaurantService sia stato chiamato*/
        verify(restaurantService)
                .findAll();

        /*verifica dei risultati*/
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getRestaurants().size()).isEqualTo(restaurants.size());

        GetRestaurantResponse response1 = response.getBody().getRestaurants().get(0);
        assertThat(response1.getName()).isEqualTo(RESTAURANT_NAME);
        assertThat(response1.getAddress()).isEqualTo(RESTAURANT_ADDRESS);

        GetRestaurantResponse response2 = response.getBody().getRestaurants().get(1);
        assertThat(response2.getName()).isEqualTo(RESTAURANT_NAME_2);
        assertThat(response2.getAddress()).isEqualTo(RESTAURANT_ADDRESS_2);

    }
}
