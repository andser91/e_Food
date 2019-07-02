package it.uniroma3.web;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CreateOrderRequest {

    private Long consumerId;
    private Long restaurantId;
    private Long ticketId;
    private List<LineItem> lineItems;

}