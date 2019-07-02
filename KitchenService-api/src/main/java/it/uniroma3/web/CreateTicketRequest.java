package it.uniroma3.web;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketRequest {

    private Long restaurantId;
    private Long orderId;
}
