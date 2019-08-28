package com.efood.restClient.thread;

import com.efood.restClient.adapter.EFoodServiceAdapter;
import com.efood.restClient.util.RandomGenerator;
import it.uniroma3.web.CreateUserResponse;
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
        CreateUserResponse createUserResponse = restTemplateAdapter.registration(RandomGenerator.randomUsername(8), RandomGenerator.randomPassword(10),
                RandomGenerator.randomName(RandomGenerator.randomNumber(4,12)),RandomGenerator.randomName(RandomGenerator.randomNumber(3,15)));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String jwt = restTemplateAdapter.login(createUserResponse.getUsername(), createUserResponse.getPassword());
        System.out.println(jwt);

        GetRestaurantMenuResponse restaurantMenuResponse = restTemplateAdapter.getMenu((long)number);
        for (RestaurantMenuItem item : restaurantMenuResponse.getMenuItems()){
            System.out.println("name: " + item.getName()+ " - price: " + item.getPrice());
        }
    }
}
