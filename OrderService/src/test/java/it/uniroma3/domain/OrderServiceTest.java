package it.uniroma3.domain;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;


@SpringBootTest
public class OrderServiceTest {

    private static final Long ORDER_ID = 12L;
    private static final Long RESTAURANT_ID = 5L;
    private static final long CONSUMER_ID = 8L;
    private List<OrderLineItem> lineItems;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        lineItems = new ArrayList<>();
        lineItems.add(new OrderLineItem("fish",2));
    }


    @Test
    public void findOrderByIdTest(){
        /*    configura orderRepository.findById per trovare l'ordine   */
        when(orderRepository.findById(ORDER_ID)).
                then(invocation -> {
                    Order order = new Order(RESTAURANT_ID, CONSUMER_ID, lineItems);
                    order.setId(ORDER_ID);
                    return Optional.of(order);
        });

        /* invoca la ricerca dell'ordine */
        Order order = orderService.findById(ORDER_ID);

        /* verifica che l'ordine è stato trovato */
        verify(orderRepository).findById(same(ORDER_ID));

        assertThat(order.getConsumerId()).isEqualTo(CONSUMER_ID);
        assertThat(order.getRestaurantId()).isEqualTo(RESTAURANT_ID);
        assertThat(order.getOrderLineItems()).isEqualTo(lineItems);
    }


}
