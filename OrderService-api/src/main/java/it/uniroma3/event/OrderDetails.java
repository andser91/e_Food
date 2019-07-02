package it.uniroma3.event;

import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    private List<LineItem> lineItems;
    private Long restaurantId;
    private Long consumerId;

}
