package com.efood.restClient.thread;

import com.efood.restClient.adapter.EFoodServiceAdapter;
import com.efood.restClient.util.RandomGenerator;
import it.uniroma3.web.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Scope("prototype")
@Setter
@RequiredArgsConstructor
public class ClientSimulator implements Runnable {

    private final EFoodServiceAdapter restTemplateAdapter;

    @Override
    public void run() {

        //registration
        CreateUserResponse createUserResponse = restTemplateAdapter.registration(RandomGenerator.randomUsername(8), RandomGenerator.randomPassword(10),
                RandomGenerator.randomName(RandomGenerator.randomNumber(4,12)),RandomGenerator.randomName(RandomGenerator.randomNumber(3,15)));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //login
        String jwt = restTemplateAdapter.login(createUserResponse.getUsername(), createUserResponse.getPassword());
        System.out.println(jwt);


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //getMenu
        long number = RandomGenerator.randomNumber(1,6);
        GetRestaurantMenuResponse restaurantMenuResponse = restTemplateAdapter.getMenu((long)number);
        for (RestaurantMenuItem item : restaurantMenuResponse.getMenuItems()){
            System.out.println("name: " + item.getName()+ " - price: " + item.getPrice());
        }


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //createOrder
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setRestaurantId(number);
        createOrderRequest.setConsumerId(createUserResponse.getId());
        double totalPrice = 0;
        List<LineItem> orderLineList = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            int itemNumber = RandomGenerator.randomNumber(0, restaurantMenuResponse.getMenuItems().size()-1);
            RestaurantMenuItem restaurantMenuItem = restaurantMenuResponse.getMenuItems().get(itemNumber);
            LineItem lineItem = new LineItem();
            lineItem.setMenuItemId(restaurantMenuItem.getItemId());
            lineItem.setQuantity(RandomGenerator.randomNumber(1,3));
            totalPrice += lineItem.getQuantity()*restaurantMenuItem.getPrice();
            orderLineList.add(lineItem);
        }
        createOrderRequest.setLineItems(orderLineList);
        createOrderRequest.setTotalPrice(totalPrice);

        CreateOrderResponse createOrderResponse = restTemplateAdapter.createOrder(createOrderRequest, jwt);
    }
}