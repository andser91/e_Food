package it.uniroma3.domain;


import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrderLineItemTest {

    private OrderLineItem lineItem;
    private final static String MENU_ITEM_ID = "pasta";
    private final static int QUANTITY = 3;

    @Before
    public void setup(){
        lineItem = new OrderLineItem(MENU_ITEM_ID, QUANTITY);
    }

    @Test
    public void getterTest(){

        assertThat(lineItem.getQuantity()).isEqualTo(QUANTITY);
        assertThat(lineItem.getMenuItemId()).isEqualTo(MENU_ITEM_ID);
    }
}
