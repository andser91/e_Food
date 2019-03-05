package it.uniroma3.adapter;

import it.uniroma3.domain.RestaurantServiceAdapter;

public class RestaurantServiceAdapterRest implements RestaurantServiceAdapter {
    @Override
    public boolean validateRestaurant(Long id) {

        return false;
    }
}
