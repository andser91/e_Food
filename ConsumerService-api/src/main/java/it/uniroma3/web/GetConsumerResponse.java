package it.uniroma3.web;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GetConsumerResponse {

    private Long consumerId;
    private String firstName;
    private String lastName;

}
