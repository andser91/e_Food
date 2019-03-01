package it.uniroma3.web;

import java.util.List;

public class GetOrdersResponse {
    private List<GetOrderResponse> restaurants;

    public GetOrdersResponse() {
    }

    public GetOrdersResponse(List<GetOrderResponse> restaurants) {
        this.restaurants = restaurants;
    }

    public List<GetOrderResponse> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<GetOrderResponse> restaurants) {
        this.restaurants = restaurants;
    }
}
