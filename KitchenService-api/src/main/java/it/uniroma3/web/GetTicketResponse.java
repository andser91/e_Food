package it.uniroma3.web;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GetTicketResponse {

    private Long ticketId;
    private Long restaurantId;
    private String ticketState;
}
