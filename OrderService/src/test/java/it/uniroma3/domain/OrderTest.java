package it.uniroma3.domain;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderTest {

    private Order order;
    private static final Long CONSUMER_ID = 5L;
    private static final Long RESTAURANT_ID = 3L;
    private List<OrderLineItem> lineItems;

    @Before
    public void setup(){
        lineItems = new ArrayList<OrderLineItem>();
        lineItems.add(new OrderLineItem("fish",2));
        order = new Order(RESTAURANT_ID, CONSUMER_ID,lineItems);
        order.setOrderState(OrderState.APPROVED);
    }

    /* test per i metodi getter */
    @Test
    public void getterTest(){
        assertThat(order.getRestaurantId()).isEqualTo(RESTAURANT_ID);
        assertThat(order.getConsumerId()).isEqualTo(CONSUMER_ID);
        assertThat(order.getOrderLineItems().size()).isEqualTo(1);
        assertThat(order.getOrderState()).isEqualTo(OrderState.APPROVED);
        assertThat(order.getOrderLineItems().get(0).getMenuItemId()).isEqualTo("fish");
        assertThat(order.getOrderLineItems().get(0).getQuantity()).isEqualTo(2);
    }
}
