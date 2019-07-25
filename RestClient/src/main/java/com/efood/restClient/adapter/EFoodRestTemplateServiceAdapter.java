package com.efood.restClient.adapter;

import it.uniroma3.web.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class EFoodRestTemplateServiceAdapter implements EFoodServiceAdapter {

    //@Value("${efood.uri}")
    private String efoodUri = "http://localhost:8080";

    @Override
    public GetRestaurantsResponse getRestaurants() {
        String restaurantUrl = efoodUri + "/restaurant/restaurants/";
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
    public CreateUserResponse registration(String username, String password, String firstname, String lastname){
        String registrationUrl = efoodUri + "/registration";
        CreateUserRequest userRequest = new CreateUserRequest(username,password,firstname,lastname);
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
    public GetRestaurantMenuResponse getMenu(Long id){
        String menuUrl = efoodUri + "/restaurant/restaurants/"+id+"/menu";
        GetRestaurantMenuResponse restaurantMenuResponse = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<GetRestaurantMenuResponse> entity = restTemplate.getForEntity(menuUrl, GetRestaurantMenuResponse.class);
            restaurantMenuResponse = entity.getBody();
        }
        catch (RestClientException e) {
            e.printStackTrace();
        }
        return restaurantMenuResponse;
    }

    @Override
    public String login(String username, String password){
        String loginUrl = efoodUri + "/login";
        JwtAuthenticationRequest request = new JwtAuthenticationRequest(username,password);
        JwtAuthenticationResponse response = null;
        RestTemplate restTemplate = new RestTemplate();
        String jwt = "";
        try {
            ResponseEntity<JwtAuthenticationResponse> authResponse = restTemplate.postForEntity(loginUrl,request, JwtAuthenticationResponse.class);
            response = authResponse.getBody();
            HttpHeaders headers = authResponse.getHeaders();
            jwt = headers.getFirst("x-auth");
            System.out.println(jwt);

        }
        catch (RestClientException e){
            e.printStackTrace();
        }
        return jwt;
    }
}
