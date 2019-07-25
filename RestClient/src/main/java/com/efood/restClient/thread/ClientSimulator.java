package com.efood.restClient.thread;

import com.efood.restClient.adapter.EFoodServiceAdapter;
import it.uniroma3.web.GetRestaurantMenuResponse;
import it.uniroma3.web.RestaurantMenuItem;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Setter
public class ClientSimulator implements Runnable {

    private int number;
    @Autowired
    private EFoodServiceAdapter restTemplateAdapter;

    @Override
    public void run() {
        System.out.println("-----------------------------------\nthread -> " + number + "\n");
        GetRestaurantMenuResponse restaurantMenuResponse = restTemplateAdapter.getMenu((long)number);
        for (RestaurantMenuItem item : restaurantMenuResponse.getMenuItems()){
            System.out.println("name: " + item.getName()+ " - price: " + item.getPrice());
        }
    }
}
