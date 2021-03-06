package it.uniroma3.web;

import it.uniroma3.domain.Order;
import it.uniroma3.domain.OrderLineItem;
import it.uniroma3.domain.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import org.springframework.http.MediaType;

@SpringBootTest
public class OrderControllerMvcMockTest {

    @InjectMocks
    private OrderController orderController;
    @Mock
    private OrderService orderService;
    private MockMvc mockMvc;

    private static final Long ORDER_ID = 15L;
    private static final Long CONSUMER_ID = 6L;
    private static final Long RESTAURANT_ID = 8L;
    private static final Long NON_EXISTING_ORDER_ID = 7L;
    private List<OrderLineItem> lineItems;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(orderController).build();
        lineItems = new ArrayList<>();
        lineItems.add(new OrderLineItem("fish",2));
    }

    @Test
    public void getTest() throws Exception {
        when(orderService.findById(ORDER_ID))
                .then(invocation -> {
                    Order order = new Order(CONSUMER_ID, RESTAURANT_ID, lineItems);
                    order.setId(ORDER_ID);
                    return order;
                });
        mockMvc
                .perform(get("/orders/{orderId}", ORDER_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(ORDER_ID))
                .andExpect(jsonPath("$.consumerId").value(CONSUMER_ID))
                .andExpect(jsonPath("$.restaurantId").value(RESTAURANT_ID))
                .andExpect(jsonPath("$.lineItems").isArray())
                .andExpect(jsonPath("$.lineItems").isNotEmpty());
    }

    @Test
    public void getOrderNotFoundTest() throws Exception {
        /* configura orderService.findById per NON trovare l'ordine  */
        when(orderService.findById(NON_EXISTING_ORDER_ID))
                .then(invocation -> {
                    return null;
                });
        mockMvc
                .perform(get("/orders/{orderId}", ORDER_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}


