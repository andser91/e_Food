package com.efood.restClient.adapter;

import it.uniroma3.web.*;
import org.springframework.http.ResponseEntity;

public interface EFoodServiceAdapter {

    GetRestaurantsResponse getRestaurants();

    CreateUserResponse registration(String username, String email, String password, String firstname, String lastname);

    String login(String username, String password);

    ResponseEntity<GetRestaurantMenuResponse> getMenu(Long id);

    CreateOrderResponse createOrder(CreateOrderRequest request, String jwt, String version);


}
