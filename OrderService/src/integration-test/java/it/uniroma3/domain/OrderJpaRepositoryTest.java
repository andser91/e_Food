package it.uniroma3.domain;

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
public class OrderJpaRepositoryTest {

    private static final Long CONSUMER_ID = 42L;
    private static final Long RESTAURANT_ID = 242L;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void saveAndFindOrderTest() {

        long orderId = transactionTemplate.execute((ts) -> {
            List<OrderLineItem> orderLineItems = new ArrayList<>();
            orderLineItems.add(new OrderLineItem("Pizza", 2));
            Order order = new Order(RESTAURANT_ID, CONSUMER_ID, orderLineItems);
            order = orderRepository.save(order);
            return order.getId();
        });

        transactionTemplate.execute((ts) -> {
            Order order = orderRepository.findById(orderId).get();
            assertThat(order).isNotNull();
            assertThat(order.getConsumerId()).isEqualTo(CONSUMER_ID);
            assertThat(order.getRestaurantId()).isEqualTo(RESTAURANT_ID);
            assertThat(order.getOrderLineItems().size()).isEqualTo(1);
            assertThat(order.getOrderState()).isEqualTo(OrderState.PENDING);
            return null;
        });
    }

    @Test
    public void saveAndDeleteOrderTest() {
        long orderId = transactionTemplate.execute((ts) -> {
            List<OrderLineItem> orderLineItems = new ArrayList<>();
            orderLineItems.add(new OrderLineItem("Pizza", 2));
            Order order = new Order(RESTAURANT_ID, CONSUMER_ID, orderLineItems);
            order = orderRepository.save(order);
            return order.getId();
        });

        transactionTemplate.execute((ts) -> {
            orderRepository.deleteById(orderId);
            return null;
        });

        transactionTemplate.execute((ts) -> {
            Optional<Order> order = orderRepository.findById(orderId);
            assertThat(order).isEmpty();
            return null;
        });
    }

    @Test
    public void saveAndFindAllTest(){

        transactionTemplate.execute((ts) -> {
            List<OrderLineItem> orderLineItems = new ArrayList<>();
            orderLineItems.add(new OrderLineItem("Pizza", 2));
            Order order = new Order(RESTAURANT_ID, CONSUMER_ID, orderLineItems);
            order = orderRepository.save(order);
            return null;
        });

        transactionTemplate.execute((ts) -> {
            List<Order> orders = (List<Order>) orderRepository.findAll();
            assertThat(orders).isNotNull();
            assertThat(orders).isNotEmpty();
            assertThat(orders.size()).isEqualTo(1);
            assertThat(orders.get(0).getConsumerId()).isEqualTo(CONSUMER_ID);
            assertThat(orders.get(0).getRestaurantId()).isEqualTo(RESTAURANT_ID);
            assertThat(orders.get(0).getOrderLineItems().size()).isEqualTo(1);
            assertThat(orders.get(0).getOrderState()).isEqualTo(OrderState.PENDING);
            return null;
        });
    }

    @Test
    public void findAllWithNoOrdersTest(){
        transactionTemplate.execute((ts) -> {
            orderRepository.deleteAll();
            return null;
        });

        transactionTemplate.execute((ts) -> {
            List<Order> orders = (List<Order>) orderRepository.findAll();
            assertThat(orders).isNotNull();
            assertThat(orders).isEmpty();
            return null;
        });
    }

}
