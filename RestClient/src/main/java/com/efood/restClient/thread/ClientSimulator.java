package com.efood.restClient.thread;

import com.efood.restClient.adapter.EFoodServiceAdapter;
import com.efood.restClient.util.RandomGenerator;
import it.uniroma3.web.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
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
        CreateUserResponse createUserResponse = restTemplateAdapter.registration(RandomGenerator.randomUsername(8),
                RandomGenerator.randomName(8)+"@"+RandomGenerator.randomName(8)+".com",
                RandomGenerator.randomPassword(10),
                RandomGenerator.randomName(RandomGenerator.randomNumber(4,12)),RandomGenerator.randomName(RandomGenerator.randomNumber(3,15)));

        try {
            Thread.sleep(RandomGenerator.randomNumber(1,3)*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //login
        String jwt = restTemplateAdapter.login(createUserResponse.getUsername(), createUserResponse.getPassword());
        System.out.println("TOKEN: " + jwt);


        try {
            Thread.sleep(RandomGenerator.randomNumber(1,3)*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //getMenu
        long restaurantId = RandomGenerator.randomNumber(1,6);
        ResponseEntity<GetRestaurantMenuResponse> entity = restTemplateAdapter.getMenu((long)restaurantId);
        String version = entity.getHeaders().getFirst("version");
        GetRestaurantMenuResponse restaurantMenuResponse = entity.getBody();
        for (RestaurantMenuItem item : restaurantMenuResponse.getMenuItems()){
            System.out.println("id: " +item.getItemId() +" - name: " + item.getName()+ " - price: " + item.getPrice());
        }

        System.out.println("------------------  Version: "+entity.getHeaders().getFirst("version"));
        try {
            Thread.sleep(RandomGenerator.randomNumber(1,3)*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (version.equals("v1")) {
            for (int i = 0; i < RandomGenerator.randomNumber(1, 3); i++) {
                makeOrder(restaurantId, createUserResponse.getId(), restaurantMenuResponse, jwt, version);
            }
        } else if (version.equals("v2")) {
            for (int i = 0; i < RandomGenerator.randomNumber(2, 4); i++) {
                makeOrder(restaurantId, createUserResponse.getId(), restaurantMenuResponse, jwt, version);
            }
        }
    }

    private void makeOrder(long restaurantId, long userId, GetRestaurantMenuResponse restaurantMenuResponse, String jwt, String version){
        //createOrder
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setRestaurantId(restaurantId);
        createOrderRequest.setConsumerId(userId);
        double totalPrice = 0;
        List<LineItem> orderLineList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int itemNumber = RandomGenerator.randomNumber(0, restaurantMenuResponse.getMenuItems().size() - 1);
            RestaurantMenuItem restaurantMenuItem = restaurantMenuResponse.getMenuItems().get(itemNumber);
            LineItem lineItem = new LineItem();
            lineItem.setMenuItemId(restaurantMenuItem.getItemId());
            lineItem.setQuantity(RandomGenerator.randomNumber(1, 3));
            totalPrice += lineItem.getQuantity() * restaurantMenuItem.getPrice();
            orderLineList.add(lineItem);
        }
        createOrderRequest.setLineItems(orderLineList);
        createOrderRequest.setTotalPrice(totalPrice);
        CreateOrderResponse createOrderResponse = restTemplateAdapter.createOrder(createOrderRequest, jwt, version);
    }
}