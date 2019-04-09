/*package it.uniroma3.adapter.restaurantService.web;

import it.uniroma3.domain.RestaurantServiceAdapter;
import it.uniroma3.web.GetRestaurantResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestaurantServiceAdapterRestTemplate implements RestaurantServiceAdapter {
    @Value("${efood.restaurantservice.uri}")
    private String restaurantServiceUri;

    @Override
    public boolean validateRestaurant(Long restaurantId) {
        String restaurantUrl = restaurantServiceUri + "/restaurants/{restaurantId}";
        GetRestaurantResponse restaurantResponse = null;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<GetRestaurantResponse> entity = restTemplate.getForEntity(restaurantUrl, GetRestaurantResponse.class, restaurantId);
            restaurantResponse = entity.getBody();
        } catch (RestClientException e) {
            //logger
        }
        return restaurantResponse != null;
    }
}
*/