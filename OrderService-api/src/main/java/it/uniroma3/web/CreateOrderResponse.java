package it.uniroma3.web;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class CreateOrderResponse {

    private Long orderId;
    private Long consumerId;
    private Long restaurantId;
    private Long ticketId;
    private String orderState;
    private double totalPrice;

    public CreateOrderResponse(Long orderId, Long consumerId, Long restaurantId, String orderState) {
        this.orderId = orderId;
        this.consumerId = consumerId;
        this.restaurantId = restaurantId;
        this.orderState = orderState;
    }

    public CreateOrderResponse(){};

}