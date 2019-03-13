package it.uniroma3.web;

import it.uniroma3.domain.Restaurant;
import it.uniroma3.domain.RestaurantService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class RestaurantControllerMockMvcTests {

    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private RestaurantService restaurantService;

    private MockMvc mockMvc;

    private static final Long RESTAURANT_ID = 1L;
    private static final String RESTAURANT_NAME = "r1";
    private static final String RESTAURANT_ADDRESS = "a1";
    private static final Restaurant restaurant1 = new Restaurant(RESTAURANT_NAME, RESTAURANT_ADDRESS);

    private static final String RESTAURANT_NAME_2 = "r2";
    private static final String RESTAURANT_ADDRESS_2 = "a2";
    private static final Restaurant restaurant2 = new Restaurant(RESTAURANT_NAME_2, RESTAURANT_ADDRESS_2);

    private List<Restaurant> restaurants;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();

        restaurants = new ArrayList<>();
        restaurants.add(restaurant1);
        restaurants.add(restaurant2);
    }

    @Test
    public void postRestaurantTest() throws Exception {
        /*verifica l'operazione POST /restauants/ */

        /*configura RestaurantService.create per creare il ristorante*/
        when (restaurantService.create(RESTAURANT_NAME, RESTAURANT_ADDRESS))
                .then(invocation -> {
                    Restaurant restaurant = new Restaurant(RESTAURANT_NAME, RESTAURANT_ADDRESS);
                    restaurant.setId(RESTAURANT_ID);
                    return restaurant;
                });

        /*invoca l'operazione POST /restaurants/*/
        /*{ "name": "rest_name", "address":"rest_address"}*/
        String jsonRequest =
                "{" +
                        "\"name\": \"" + RESTAURANT_NAME + "\"," +
                        "\"address\": \"" + RESTAURANT_ADDRESS + "\"" +
                "}";

        mockMvc
                .perform(post("/restaurants/").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
                .andExpect(status().isOk())
                /*verifica gli elementi della risposta JSON*/
                .andExpect(jsonPath("$.restaurantId").value(RESTAURANT_ID));
    }

    @Test
    public void getRestaurantTest() throws Exception {
        /*verifica operazione GET /restaurants/{restaurantId}*/

        when(restaurantService.findById(RESTAURANT_ID))
                .then(invocation -> {
                    Restaurant restaurant = new Restaurant(RESTAURANT_NAME, RESTAURANT_ADDRESS);
                    restaurant.setId(RESTAURANT_ID);
                    return  restaurant;
                });

        mockMvc
                .perform(get("/restaurants/{restaurantId}", RESTAURANT_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(RESTAURANT_ID))
                .andExpect(jsonPath("$.name").value(RESTAURANT_NAME))
                .andExpect(jsonPath("$.address").value(RESTAURANT_ADDRESS));
    }

    @Test
    public void getRestaurantNotFoundTest() throws Exception{
        /*verifica dell'operazione GET /restaurants/{restaurantId} nel caso in cui il ristorante non sia prensente*/

        when(restaurantService.findById(RESTAURANT_ID)).thenReturn(null);

        mockMvc
                .perform(get("/restaurants/{restaurantId}", RESTAURANT_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTwoRestaurantsTest() throws Exception {
        /*verifica dell'operazione GET /restaurants/ con due ristoranti presenti*/

        when(restaurantService.findAll())
                .then(invocation -> {
                    List<Restaurant> restaurantResponse = new ArrayList<Restaurant>(restaurants);
                    return restaurantResponse;
                });

        mockMvc
                .perform(get("/restaurants/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurants.size()").value(restaurants.size()))
                /*verifica primo ristorante*/
                .andExpect(jsonPath("$.restaurants[0]").value(restaurants.get(0)))
                .andExpect(jsonPath("$.restaurants[0].name").value(restaurants.get(0).getName()))
                .andExpect(jsonPath("$.restaurants[0].address").value(restaurants.get(0).getAddress()))
                /*verifica secondo ristorante*/
                .andExpect(jsonPath("$.restaurants[1]").value(restaurants.get(1)))
                .andExpect(jsonPath("$.restaurants[1].name").value(restaurants.get(1).getName()))
                .andExpect(jsonPath("$.restaurants[1].address").value(restaurants.get(1).getAddress()));
    }

    @Test
    public void getEmptyTest() throws Exception {
        /*test GET /restaurants/ nel caso in cui non siano presenti ristoranti*/
        when(restaurantService.findAll())
                .thenReturn(null);

        mockMvc
                .perform(get("/restaurants/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
