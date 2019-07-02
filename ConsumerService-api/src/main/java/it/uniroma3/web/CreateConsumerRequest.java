package it.uniroma3.web;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CreateConsumerRequest {

    private String firstName;
    private String lastName;
}
