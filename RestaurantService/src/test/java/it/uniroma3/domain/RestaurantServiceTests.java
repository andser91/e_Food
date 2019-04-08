package it.uniroma3.domain;


import it.uniroma3.RestaurantServiceChannel;
import it.uniroma3.common.event.DomainEventPublisher;
import it.uniroma3.event.RestaurantInvalidatedEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RestaurantServiceTests {

    /*we want unit tests to be independent of all class dependencies (mock object: test double)
    * we don't use the real implementation of RestaurantRepository but a mock one.*/

    /*will inject the mocks marked with @Mock to this instance when it is created*/
    @InjectMocks
    private RestaurantService restaurantService;

    /*will create a mock implementation of restaurantRepository*/
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private DomainEventPublisher domainEventPublisher;

    private static final Long VALID_ORDER_ID = 11L;
    private static final Long INVALID_ORDER_ID = 12L;

    private static final Long INVALID_RESTAURANT_ID = 12L;

    private static final Long RESTAURANT_ID = 1L;
    private static final String RESTAURANT_NAME = "r1";
    private static final String RESTAURANT_ADDRESS = "a1";

    private static final Long RESTAURANT_ID_2 = 2L;
    private static final String RESTAURANT_NAME_2 = "r2";
    private static final String RESTAURANT_ADDRESS_2 = "a2";

    private List<Restaurant> restaurants;


    @Before
    public void setup() {
        /*create the istance restaurantService and inject mocks*/
        MockitoAnnotations.initMocks(this);

        restaurants = new ArrayList<Restaurant>();
        restaurants.add(new Restaurant(RESTAURANT_NAME, RESTAURANT_ADDRESS));
        restaurants.add(new Restaurant(RESTAURANT_NAME_2, RESTAURANT_ADDRESS_2));
    }

    @Test
    public void createRestaurantTest() {
        /*verifica che quando viene usato il servizio per creare il ristorante
        * questo venga salvato nel repository
        * */

        when(restaurantRepository.save(any(Restaurant.class)))
                .then(invocation -> {
                    Restaurant restaurant = (Restaurant) invocation.getArguments()[0];
                    restaurant.setId(RESTAURANT_ID);
                    return restaurant;
                });

        Restaurant restaurant = restaurantService.create(RESTAURANT_NAME, RESTAURANT_ADDRESS);


        /*verify that the save method has been invoked*/
        verify(restaurantRepository).save(same(restaurant));
    }

    @Test
    public void findRestaurantByIdTest() {
        /*verifica che quando viene usato il servizio per cercare il risotrante
        * il ristorante viene cercato tramite il repository*/

        /*configura RestaurantRepository.findById per trovare il ristorante*/
        when(restaurantRepository.findById(RESTAURANT_ID))
                .then(invocation -> {
                    Restaurant restaurant = new Restaurant(RESTAURANT_NAME, RESTAURANT_ADDRESS);
                    restaurant.setId(RESTAURANT_ID);
                    return Optional.of(restaurant);
                });

        Restaurant restaurant = restaurantService.findById(RESTAURANT_ID);

        verify(restaurantRepository)
                .findById(same(RESTAURANT_ID));

        assertThat(restaurant.getName()).isEqualTo(RESTAURANT_NAME);
        assertThat(restaurant.getAddress()).isEqualTo(RESTAURANT_ADDRESS);
    }

    @Test
    public void deleteByIdTest(){
        restaurantService.deleteById(RESTAURANT_ID);
        /*verifica che deleteById(restId) del repository sia stato invocato una volta*/
        verify(restaurantRepository, times(1)).deleteById(RESTAURANT_ID);
    }

    @Test
    public void findAllRestaurantsWithTwoRestaurantsTest() {
        when(restaurantRepository.findAll())
                .then(invocation -> {
                    List<Restaurant> restaurantList = new ArrayList<>(restaurants);
                    return restaurantList;
                });

        List<Restaurant> restaurantList = restaurantService.findAll();

        /*verifica che il metodo di restaurantRepository sia stato invocato*/
        verify(restaurantRepository)
                .findAll();

        /*verifica il risultato*/
        Restaurant first_restaurant = restaurantList.get(0);
        assertThat(first_restaurant.getAddress()).isEqualTo(RESTAURANT_ADDRESS);
        assertThat(first_restaurant.getName()).isEqualTo(RESTAURANT_NAME);

        Restaurant second_restaurant = restaurantList.get(1);
        assertThat(second_restaurant.getAddress()).isEqualTo(RESTAURANT_ADDRESS_2);
        assertThat(second_restaurant.getName()).isEqualTo(RESTAURANT_NAME_2);
    }

    @Test
    public void validateOrderRestaurantTest() {
        /*verifica che, quando viene richiesta la validazione del ristorante di un ordine
        * se questo c'è allora viene pubblicato un evento di validazione del ristorante*/
        when(restaurantRepository.findById(RESTAURANT_ID)).
                then(invocation -> {
                    Restaurant restaurant = new Restaurant(RESTAURANT_NAME, RESTAURANT_ADDRESS);
                    restaurant.setId(RESTAURANT_ID);
                    return Optional.of(restaurant);
                });

        restaurantService.validateOrderRestaurant(VALID_ORDER_ID, RESTAURANT_ID);

        /*verifica che il ristorante sia stato cercato*/
        verify(restaurantRepository).findById(same(RESTAURANT_ID));
        /*verifica che l'evento di validazione del ristorante dell'ordine sia stato creato*/
        //verify(domainEventPublisher).
        //        publish(new RestaurantValidatedEvent(VALID_ORDER_ID, RESTAURANT_ID), RestaurantServiceChannel.restaurantServiceChannel);
    }


    @Test
    public void invalidateOrderRestaurantTest() {
        /*verifica che, quando viene richiesta la validazione del ristorante di un ordine
         * se questo non è presente allora viene pubblicato un evento di invalidazione del ristorante*/

        when(restaurantRepository.findById(INVALID_RESTAURANT_ID)).thenReturn(Optional.empty());

        restaurantService.validateOrderRestaurant(INVALID_ORDER_ID, INVALID_RESTAURANT_ID);

        /*verifica che il ristorante sia stato cercato*/
        verify(restaurantRepository).findById(same(INVALID_RESTAURANT_ID));
        /*verifica che l'evento di invalidazione del ristorante dell'ordine sia stato creato*/
        verify(domainEventPublisher).
                publish(new RestaurantInvalidatedEvent(INVALID_ORDER_ID, INVALID_RESTAURANT_ID), RestaurantServiceChannel.restaurantServiceChannel);

    }
}
