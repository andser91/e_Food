package it.uniroma3.event;


import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {

    private Long menuItemId;
    private int quantity;
}
