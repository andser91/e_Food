package it.uniroma3.web;

import it.uniroma3.domain.Order;
import it.uniroma3.domain.OrderLineItem;
import it.uniroma3.domain.OrderService;
import it.uniroma3.domain.OrderState;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;

    private static final Long ORDER_ID = 15L;
    private static final Long CONSUMER_ID = 6L;
    private static final Long RESTAURANT_ID = 8L;
    private List<OrderLineItem> lineItems;
    private static final Long NON_EXISTING_ORDER_ID = 11L;
    private static final Long FIRST_ORDER_RESTAURANT_ID = 44L;
    private static final Long FIRST_ORDER_CONSUMER_ID = 22L;
    private static final Long SECOND_ORDER_RESTAURANT_ID = 54L;
    private static final Long SECOND_ORDER_CONSUMER_ID = 52L;
    private List<Order> orders;

    @Before
    public void setup() {
        /* inizializza i mock ed il servizio da testare */
        MockitoAnnotations.initMocks(this);
        lineItems = new ArrayList<>();
        lineItems.add(new OrderLineItem("fish",2));
        orders = new ArrayList<Order>();
        orders.add(new Order(FIRST_ORDER_RESTAURANT_ID,FIRST_ORDER_CONSUMER_ID,lineItems));
        orders.add(new Order(SECOND_ORDER_RESTAURANT_ID,SECOND_ORDER_CONSUMER_ID,lineItems));
    }

    /*  verifica dell'operazione GET /orders/{orderId}  */
    @Test
    public void getOrderTest(){
        /* configura orderService.findById per trovare l'ordine  */
        when(orderService.findById(ORDER_ID))
                .then(invocation -> {
                    Order order = new Order(RESTAURANT_ID, CONSUMER_ID, lineItems);
                    order.setId(ORDER_ID);
                    return order;
                });
        /* invoca l'operazione GET /orders/{orderId}  */
        ResponseEntity<GetOrderResponse> responseEntity = orderController.findById(ORDER_ID);

        /* verifica che il servizio è stato invocato */
        verify(orderService).findById(ORDER_ID);

        /* verifica la risposta */
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        GetOrderResponse response = responseEntity.getBody();
        assertThat(response.getOrderId()).isEqualTo(ORDER_ID);
        assertThat(response.getConsumerId()).isEqualTo(CONSUMER_ID);
        assertThat(response.getRestaurantId()).isEqualTo(RESTAURANT_ID);
        assertThat(response.getLineItems().size()).isEqualTo(1);
        assertThat(response.getOrderState()).isEqualTo("PENDING");
    }

    /*  verifica dell'operazione GET /orders/{orderId} nel caso in cui l'order non viene trovato */
    @Test
    public void getOrderNotFoundTest(){
        /* configura orderService.findById per NON trovare l'ordine  */
        when(orderService.findById(NON_EXISTING_ORDER_ID))
                .then(invocation -> {
                    return null;
                });

        /* invoca l'operazione GET /orders/{orderId}  */
        ResponseEntity<GetOrderResponse> responseEntity = orderController.findById(NON_EXISTING_ORDER_ID);

        /* verifica che il servizio è stato invocato */
        verify(orderService).findById(NON_EXISTING_ORDER_ID);

        /* verifica la risposta */
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        GetOrderResponse response = responseEntity.getBody();
        assertThat(response).isNull();
    }

    /*  verifica dell'operazione GET /orders/ nel caso in cui ci sono 2 ordini */
    @Test
    public void getOrdersTestWithTwoOrders(){
        /* configura orderService.findAll per trovare tutti gli ordini  */
        when(orderService.findAll())
                .then(invocation -> {
                    List<Order> orderList = new ArrayList<>(orders);
                    return orderList;
                });

        /* invoca l'operazione GET /orders/  */
        ResponseEntity<GetOrdersResponse> responseEntity = orderController.findAll();

        /* verifica che il servizio è stato invocato */
        verify(orderService).findAll();

        /* verifica la risposta */
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        GetOrdersResponse response = responseEntity.getBody();
        assertThat(response.getOrders().size()).isEqualTo(2);
        //firstOrder
        assertThat(response.getOrders().get(0).getLineItems().size()).isEqualTo(1);
        assertThat(response.getOrders().get(0).getConsumerId()).isEqualTo(FIRST_ORDER_CONSUMER_ID);
        assertThat(response.getOrders().get(0).getRestaurantId()).isEqualTo(FIRST_ORDER_RESTAURANT_ID);
        assertThat(response.getOrders().get(0).getOrderState()).isEqualTo("PENDING");
        //secondOrder
        assertThat(response.getOrders().get(1).getLineItems().size()).isEqualTo(1);
        assertThat(response.getOrders().get(1).getConsumerId()).isEqualTo(SECOND_ORDER_CONSUMER_ID);
        assertThat(response.getOrders().get(1).getRestaurantId()).isEqualTo(SECOND_ORDER_RESTAURANT_ID);
        assertThat(response.getOrders().get(1).getOrderState()).isEqualTo("PENDING");
    }

    /*  verifica dell'operazione GET /orders/ nel caso in cui non ci sono ordini */
    @Test
    public void getOrdersTestEmptyList(){
        /* configura orderService.findAll per trovare tutti gli ordini  */
        when(orderService.findAll())
                .then(invocation -> {
                   return null;
                });

        /* invoca l'operazione GET /orders/  */
        ResponseEntity<GetOrdersResponse> responseEntity = orderController.findAll();

        /* verifica che il servizio è stato invocato */
        verify(orderService).findAll();

        /* verifica la risposta */
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        GetOrdersResponse response = responseEntity.getBody();
        assertThat(response).isNull();

    }
}
