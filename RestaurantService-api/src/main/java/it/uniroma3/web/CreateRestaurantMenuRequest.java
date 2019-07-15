package it.uniroma3.web;

import lombok.Data;

import java.util.*;

@Data
public class CreateRestaurantMenuRequest {

    private List<RestaurantMenuItem> menuItems;
}

