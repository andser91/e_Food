package it.uniroma3.web;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {

    private Long menuItemId;
    private int quantity;

}
