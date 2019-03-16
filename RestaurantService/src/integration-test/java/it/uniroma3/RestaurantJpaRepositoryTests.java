package it.uniroma3;

import it.uniroma3.domain.Restaurant;
import it.uniroma3.domain.RestaurantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantJpaRepositoryTests {

    public static final String RESTAURANT_NAME = "r1";
    public static final String RESTAURANT_ADDRESS = "a1";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void saveAndFindRestaurantTest() {
        Long restaurantId = transactionTemplate.execute((ts) ->{
            Restaurant restaurant = new Restaurant(RESTAURANT_NAME, RESTAURANT_ADDRESS);
            restaurant = restaurantRepository.save(restaurant);
            return restaurant.getId();
        });

        transactionTemplate.execute((ts)->{
            Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

            assertThat(restaurant).isNotNull();
            assertThat(restaurant.getName()).isEqualTo(RESTAURANT_NAME);
            assertThat(restaurant.getAddress()).isEqualTo(RESTAURANT_ADDRESS);
            return null;
        });
    }

    @Test
    public void saveAndDeleteRestaurantTest() {
        Long restaurantId = transactionTemplate.execute((ts) ->{
            Restaurant restaurant = new Restaurant(RESTAURANT_NAME, RESTAURANT_ADDRESS);
            restaurant = restaurantRepository.save(restaurant);
            return restaurant.getId();
        });

        transactionTemplate.execute((ts)-> {
            Restaurant existingRestaurant = restaurantRepository.findById(restaurantId).get();
            assertThat(existingRestaurant).isNotNull();
            restaurantRepository.deleteById(restaurantId);
            Optional<Restaurant> removedRestaurant = restaurantRepository.findById(restaurantId);
            assertThat(removedRestaurant.isPresent()).isFalse();

            return null;
        });
    }
}
