package it.uniroma3.web;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GetRestaurantsResponse {
    private List<GetRestaurantResponse> restaurants;

}
