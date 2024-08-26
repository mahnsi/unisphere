package model;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {
    private List<Product> items;

    public Wishlist() {
        this.items = new ArrayList<>();
    }

   
    public void add(Product product) {
        items.add(product);
    }

   
    public void remove(int productId) {
        items.removeIf(product -> product.getId() == productId);
    }

 
    public List<Product> getItems() {
        return items;
    }

   
    public void setItems(List<Product> items) {
        this.items = items;
    }
}
