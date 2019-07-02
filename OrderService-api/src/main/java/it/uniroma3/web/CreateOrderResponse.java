package it.uniroma3.web;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateOrderResponse {

    private Long orderId;
    private Long consumerId;
    private Long restaurantId;
    private Long ticketId;
    private String orderState;

    public CreateOrderResponse(Long orderId, Long consumerId, Long restaurantId, String orderState) {
        this.orderId = orderId;
        this.consumerId = consumerId;
        this.restaurantId = restaurantId;
        this.orderState = orderState;
    }

}