package com.efood.restClient.adapter;

import it.uniroma3.web.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class EFoodRestTemplateServiceAdapter implements EFoodServiceAdapter {

    //@Value("${efood.uri}")
    private String efoodUri = "http://192.168.1.12:31380";

    @Override
    public GetRestaurantsResponse getRestaurants() {
        String restaurantUrl = efoodUri + "/restaurant-service/restaurants/";
        GetRestaurantsResponse restaurantsResponse = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<GetRestaurantsResponse> entity = restTemplate.getForEntity(restaurantUrl, GetRestaurantsResponse.class);
            restaurantsResponse = entity.getBody();
        }
        catch (RestClientException e) {
            e.printStackTrace();
        }
        return restaurantsResponse;
    }

    @Override
    public CreateUserResponse registration(String username, String email,String password, String firstname, String lastname){
        String registrationUrl = efoodUri + "/iam/user/sign-up";
        CreateUserRequest userRequest = new CreateUserRequest(username,email,password,firstname,lastname);
        CreateUserResponse userResponse = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            userResponse = restTemplate.postForObject(registrationUrl,userRequest, CreateUserResponse.class);
        }
        catch (RestClientException e){
            e.printStackTrace();
        }
        return userResponse;
    }

    @Override
    public String login(String username, String password){
        String loginUrl = efoodUri + "/iam/login";
        JwtAuthenticationRequest request = new JwtAuthenticationRequest(username,password);
        JwtAuthenticationResponse response = null;
        RestTemplate restTemplate = new RestTemplate();
        String jwt = "";
        try {
            ResponseEntity<JwtAuthenticationResponse> authResponse = restTemplate.postForEntity(loginUrl,request, JwtAuthenticationResponse.class);
            response = authResponse.getBody();
            HttpHeaders headers = authResponse.getHeaders();
            jwt = headers.getFirst("Authorization");
        }
        catch (RestClientException e){
            e.printStackTrace();
        }
        return jwt;
    }

    @Override
    public ResponseEntity<GetRestaurantMenuResponse> getMenu(Long id){
        String menuUrl = efoodUri + "/restaurant-service/restaurants/"+id+"/menu";
        ResponseEntity<GetRestaurantMenuResponse> entity = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            entity = restTemplate.getForEntity(menuUrl, GetRestaurantMenuResponse.class);
        }
        catch (RestClientException e) {
            e.printStackTrace();
        }
        return entity;
    }



    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request, String jwt, String version){
        String createOrderUrl = efoodUri + "/order-service/orders/";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        headers.add("version", version);
        HttpEntity<CreateOrderRequest> entity = new HttpEntity<>(request, headers);
        CreateOrderResponse createOrderResponse = null;
        try {
            ResponseEntity<CreateOrderResponse> orderResponse = restTemplate.postForEntity(createOrderUrl,entity,CreateOrderResponse.class);
            createOrderResponse = orderResponse.getBody();

        }
        catch (RestClientException e) {
            e.printStackTrace();
        }
        return  createOrderResponse;
    }
}
