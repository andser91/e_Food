package it.uniroma3.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    @Embedded
    private RestaurantMenu menu;



    public Restaurant(String name, String city) {
        super();
        this.name = name;
        this.city = city;
    }

    public Restaurant(String name, String city, RestaurantMenu menu) {
        this(name, city);
        this.menu = menu;
    }

    public static Restaurant create(String name, String city, RestaurantMenu menu) {
        return new Restaurant(name, city, menu);
    }

    public static Restaurant create(String name, String city) {
        return new Restaurant(name, city);
    }
}
