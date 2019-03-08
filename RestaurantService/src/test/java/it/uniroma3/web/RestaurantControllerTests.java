package it.uniroma3.web;

import it.uniroma3.domain.Restaurant;
import it.uniroma3.domain.RestaurantService;
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

    private static final String RESTAURANT_NAME_2 = "r2";
    private static final String RESTAURANT_ADDRESS_2 = "a2";

    private static final Long RESTAURANT_ID_NOT_FOUND = 11L;

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
        /*verifica dell'operazione GET /restaurants/ */

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

    @Test
    public void getRestaurantsNotFoundTest() {
        /*verifica dell'operazione GET /restaurants/ nel caso in cui i ristoranti non vengano trovati*/

        when(restaurantService.findAll()).thenReturn(null);

        /*invoca l'operazione GET /restaurants/*/
        ResponseEntity<GetRestaurantsResponse> responseEntity = restaurantController.findAll();

        /*verifica che il servizio sia stato invocato*/
        verify(restaurantService).findAll();

        /*verifica i risultati*/
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(null);
    }

    @Test
    public void getRestaurantTest() {
        /*verifica dell'operazione GET /restaurants/{restaurantId}*/
        when(restaurantService.findById(RESTAURANT_ID))
                .then(invocation -> {
                    Restaurant restaurant = new Restaurant(RESTAURANT_NAME, RESTAURANT_ADDRESS);
                    restaurant.setId(RESTAURANT_ID);
                    return restaurant;
                });

        ResponseEntity<GetRestaurantResponse> responseEntity = restaurantController.findById(RESTAURANT_ID);

        /*verifica che il servizio sia stato invocato*/
        verify(restaurantService)
                .findById(RESTAURANT_ID);

        /*verifica i risultati*/
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        GetRestaurantResponse response = responseEntity.getBody();
        assertThat(response.getId()).isEqualTo(RESTAURANT_ID);
        assertThat(response.getAddress()).isEqualTo(RESTAURANT_ADDRESS);
        assertThat(response.getName()).isEqualTo(RESTAURANT_NAME);
    }

    @Test
    public void getRestaurantNotFoundTest() {
        /*verifica dell'operazione GET /restaurants/{restaurantID} nel caso in cui il ristorante non venga trovato*/

        when(restaurantService.findById(RESTAURANT_ID_NOT_FOUND)).thenReturn(null);

        ResponseEntity<GetRestaurantResponse> responseEntity = restaurantController.findById(RESTAURANT_ID_NOT_FOUND);

        /*verifica che il servizio sia stato invocato*/
        verify(restaurantService)
                .findById(RESTAURANT_ID_NOT_FOUND);

        /*verifica dei risultati*/
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(null);
    }
}
