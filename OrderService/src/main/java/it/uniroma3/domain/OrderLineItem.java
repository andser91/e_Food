package it.uniroma3.domain;

import javax.persistence.Embeddable;

@Embeddable
public class OrderLineItem {

    private String menuItemId;
    private int quantity;

    public OrderLineItem() {
    }

    public OrderLineItem(String menuItemId, int quantity) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderLineItem{" +
                "menuItemId='" + menuItemId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}