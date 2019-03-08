package it.uniroma3.domain;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RestaurantServiceTests {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    private static final Long RESTAURANT_ID = 1L;
    private static final String RESTAURANT_NAME = "r1";
    private static final String RESTAURANT_ADDRESS = "a1";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
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
}
