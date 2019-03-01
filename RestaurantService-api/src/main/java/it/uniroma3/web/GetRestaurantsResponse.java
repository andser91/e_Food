package it.uniroma3.web;

import lombok.Data;

import java.util.List;

@Data
public class GetRestaurantsResponse {
    private List<GetRestaurantResponse> restaurants;

    public GetRestaurantsResponse(List<GetRestaurantResponse> restaurants) {
        this.restaurants = restaurants;
    }

    public GetRestaurantsResponse() {
    }
}
