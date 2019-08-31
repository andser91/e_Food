package com.efood.restClient.adapter;

import it.uniroma3.web.*;

public interface EFoodServiceAdapter {

    GetRestaurantsResponse getRestaurants();

    CreateUserResponse registration(String username, String password, String firstname, String lastname);

    String login(String username, String password);

    GetRestaurantMenuResponse getMenu(Long id);

    CreateOrderResponse createOrder(CreateOrderRequest request, String jwt);


}
