package it.uniroma3.event;

public class LineItem {

    private String menuItemId;
    private int quantity;

    public LineItem() {
    }

    public LineItem(String menuItemId, int quantity) {
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
        return "LineItem{" +
                "menuItemId='" + menuItemId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
