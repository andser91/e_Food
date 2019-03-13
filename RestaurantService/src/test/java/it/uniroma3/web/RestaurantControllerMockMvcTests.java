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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
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
    private static final Restaurant restaurant = new Restaurant(RESTAURANT_NAME, RESTAURANT_ADDRESS);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
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

}
