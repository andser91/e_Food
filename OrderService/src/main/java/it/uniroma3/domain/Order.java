package it.uniroma3.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Order {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long restaurantId;
    private Long consumerId;
    private Long ticketId;
    private OrderState orderState;
    @ElementCollection
    private List<OrderLineItem> orderLineItems;
    private double total;

    public Order(Long consumerId, Long restaurantId,  List<OrderLineItem> orderLineItems, double total) {
        this.restaurantId = restaurantId;
        this.consumerId = consumerId;
        this.orderState = OrderState.PENDING;
        this.orderLineItems = orderLineItems;
        this.total = total;
    }


    public static Order create(Long customerId, Long restaurantId, List<OrderLineItem> orderLineItems, double total) {
        return new Order(customerId, restaurantId, orderLineItems, total);
    }

}
