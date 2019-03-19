package it.uniroma3.domain;


import it.uniroma3.OrderServiceChannel;
import it.uniroma3.common.event.DomainEventPublisher;
import it.uniroma3.event.LineItem;
import it.uniroma3.event.OrderCreatedEvent;
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
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;


@SpringBootTest
public class OrderServiceTest {

    private static final Long ORDER_ID = 12L;
    private static final Long RESTAURANT_ID = 5L;
    private static final Long CONSUMER_ID = 8L;
    private static final Long FIRST_ORDER_RESTAURANT_ID = 44L;
    private static final Long FIRST_ORDER_CONSUMER_ID = 22L;
    private static final Long SECOND_ORDER_RESTAURANT_ID = 54L;
    private static final Long SECOND_ORDER_CONSUMER_ID = 52L;
    private List<Order> orders;
    private List<OrderLineItem> lineItems;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DomainEventPublisher domainEventPublisher;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        lineItems = new ArrayList<>();
        lineItems.add(new OrderLineItem("fish",2));
        orders = new ArrayList<Order>();
        orders.add(new Order(FIRST_ORDER_CONSUMER_ID, FIRST_ORDER_RESTAURANT_ID,null));
        orders.add(new Order(SECOND_ORDER_CONSUMER_ID,SECOND_ORDER_RESTAURANT_ID,lineItems));
    }

    /* verifica del metodo create  */
    //Per poter eseguire questo test nel metodo create di OrderService si deve scegliere la modalità asincrona
    /*
    @Test
    public void createOrderTest(){
        // configura il repository per settare l'id dell'ordine
        when(orderRepository.save(any(Order.class)))
                .then(invocation -> {
                   Order order = (Order) invocation.getArguments()[0];
                   order.setId(ORDER_ID);
                   return order;
                });
        // invoca la creazione dell'ordine
        Order order = orderService.create(CONSUMER_ID,RESTAURANT_ID,lineItems);

        // verifica che l'ordine è stato salvato
        verify(orderRepository).save(same(order));

        List<LineItem> orderlineItems = order.getOrderLineItems()
                .stream()
                .map(x -> new LineItem(x.getMenuItemId(), x.getQuantity()))
                .collect(Collectors.toList());

        // verifica che viene creato l'evento
        verify(domainEventPublisher).publish(new OrderCreatedEvent(ORDER_ID, CONSUMER_ID, RESTAURANT_ID, orderlineItems)
            ,OrderServiceChannel.orderServiceChannel);
    }
    */



    /*  verifica del metodo findById quando è presente l'ordine */
    @Test
    public void findOrderByIdTest(){
        /*    configura orderRepository.findById per trovare l'ordine   */
        when(orderRepository.findById(ORDER_ID)).
                then(invocation -> {
                    Order order = new Order(CONSUMER_ID, RESTAURANT_ID, lineItems);
                    order.setId(ORDER_ID);
                    return Optional.of(order);
        });

        /* invoca la ricerca dell'ordine */
        Order order = orderService.findById(ORDER_ID);

        /* verifica che l'ordine è stato trovato */
        verify(orderRepository).findById(same(ORDER_ID));

        assertThat(order.getConsumerId()).isEqualTo(CONSUMER_ID);
        assertThat(order.getRestaurantId()).isEqualTo(RESTAURANT_ID);
        assertThat(order.getOrderLineItems().size()).isEqualTo(1);
        assertThat(order.getOrderState()).isEqualTo(OrderState.PENDING);
    }

    /* verifica del metodo findAll quando sono presenti ordini */
    @Test
    public void findAllTestWithTwoOrders(){
        /* configura orderRepository.findAll per trovare gli ordini  */
        when(orderRepository.findAll())
                .then(invocation -> {
                   List<Order> orderList = new ArrayList<>(orders);
                   return orderList;
                });

        /* invoca la ricerca di tutti gli ordini */
        List<Order> orderList = orderService.findAll();

        /* verifica che il repository è stato usato  */
        verify(orderRepository).findAll();

        /*verifica il risultato  */
        assertThat(orderList.size()).isEqualTo(2);
          //firstOrder
        assertThat(orderList.get(0).getOrderLineItems()).isEqualTo(null);
        assertThat(orderList.get(0).getConsumerId()).isEqualTo(FIRST_ORDER_CONSUMER_ID);
        assertThat(orderList.get(0).getRestaurantId()).isEqualTo(FIRST_ORDER_RESTAURANT_ID);
        assertThat(orderList.get(0).getOrderState()).isEqualTo(OrderState.PENDING);
          //secondOrder
        assertThat(orderList.get(1).getOrderLineItems().size()).isEqualTo(1);
        assertThat(orderList.get(1).getConsumerId()).isEqualTo(SECOND_ORDER_CONSUMER_ID);
        assertThat(orderList.get(1).getRestaurantId()).isEqualTo(SECOND_ORDER_RESTAURANT_ID);
        assertThat(orderList.get(1).getOrderState()).isEqualTo(OrderState.PENDING);
    }

    /* verifica del metodo findAll quando NON sono presenti ordini */
    @Test
    public void findAllTestEmpty(){
        /* configura orderRepository.findAll per trovare gli ordini  */
        when(orderRepository.findAll())
                .then(invocation -> {
                   return null;
                });
        /* invoca la ricerca di tutti gli ordini */
        List<Order> orderList = orderService.findAll();

        /* verifica che il repository è stato usato  */
        verify(orderRepository).findAll();

        /*verifica il risultato  */
        assertThat(orderList).isNull();
    }

}
