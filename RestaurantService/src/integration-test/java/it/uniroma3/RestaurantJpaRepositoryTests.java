package it.uniroma3;

import it.uniroma3.domain.Restaurant;
import it.uniroma3.domain.RestaurantRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantJpaRepositoryTests {

    public static final String RESTAURANT_NAME = "r1";
    public static final String RESTAURANT_ADDRESS = "a1";

    public static final String RESTAURANT_NAME_2 = "r2";
    public static final String RESTAURANT_ADDRESS_2 = "a2";

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

    public void saveAndFindAllTest() {
        List<Restaurant> restaurants = transactionTemplate.execute((ts)->{
            Restaurant r1 = new Restaurant(RESTAURANT_NAME, RESTAURANT_ADDRESS);
            Restaurant r2 = new Restaurant(RESTAURANT_NAME_2, RESTAURANT_ADDRESS_2);
            List<Restaurant> restaurantList = new ArrayList<>();
            restaurantList.add(r1);
            restaurantList.add(r2);
            restaurantRepository.save(r1);
            restaurantRepository.save(r2);
            return restaurantList;
        });

        transactionTemplate.execute((ts)-> {
            List<Restaurant> restaurantsRetrived = (List<Restaurant>) restaurantRepository.findAll();

            assertThat(restaurantsRetrived.size()).isEqualTo(restaurants.size());

            Restaurant r1 = restaurantsRetrived.get(0);
            assertThat(r1.getName()).isEqualTo(RESTAURANT_NAME);
            assertThat(r1.getAddress()).isEqualTo(RESTAURANT_ADDRESS);

            Restaurant r2 = restaurantsRetrived.get(1);
            assertThat(r2.getName()).isEqualTo(RESTAURANT_NAME_2);
            assertThat(r2.getAddress()).isEqualTo(RESTAURANT_ADDRESS_2);

            return null;
        });
    }

    public void findAllEmptyTest() {
        transactionTemplate.execute((ts)->{
            restaurantRepository.deleteAll();
            return null;
        });

        transactionTemplate.execute((ts)-> {
            List<Restaurant> restaurantList = (List<Restaurant>) restaurantRepository.findAll();
            assertThat(restaurantList.isEmpty());
            return null;
        });
    }
}
