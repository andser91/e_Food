package it.uniroma3.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private String address;



    public Restaurant(String name, String address) {
        super();
        this.name = name;
        this.address = address;
    }

    public static Restaurant create(String name, String address) {
        return new Restaurant(name, address);
    }
}
