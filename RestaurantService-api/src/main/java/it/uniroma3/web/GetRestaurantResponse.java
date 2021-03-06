package it.uniroma3.web;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GetRestaurantResponse {
    private Long id;
    private String city;
    private String name;

}
