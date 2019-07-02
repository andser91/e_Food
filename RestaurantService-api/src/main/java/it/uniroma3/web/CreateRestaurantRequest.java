package it.uniroma3.web;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class CreateRestaurantRequest {
    private String address;
    private String name;

}
