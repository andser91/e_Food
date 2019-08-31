package it.uniroma3.web;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse {

    private Long orderId;
    private Long consumerId;
    private Long restaurantId;
    private Long ticketId;
    private List<LineItem> lineItems;
    private String orderState;
    private double totalPrice;
}
