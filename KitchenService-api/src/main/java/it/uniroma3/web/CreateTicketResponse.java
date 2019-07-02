package it.uniroma3.web;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketResponse {

    private Long orderId;
    private Long ticketId;
    private Long restaurantId;
    private String ticketState;
}
