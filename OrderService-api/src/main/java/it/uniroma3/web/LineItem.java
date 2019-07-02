package it.uniroma3.web;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {

    private String menuItemId;
    private int quantity;

}
