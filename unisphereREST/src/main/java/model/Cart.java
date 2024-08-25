package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Cart {
	User owner;
	float totalPrice;
	List <CartItem> items;
	
	private int offer;
	
	public Cart() {
		items = new ArrayList<>();

	}
	
	public void add(Product product) {
        // Check if the product already exists in the cart
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        // If the product is not found, add it as a new CartItem
        CartItem newItem = new CartItem(product, 1);
        items.add(newItem);
    }
	
	public void remove(int productid) {
        items.removeIf(item -> item.getProduct().getId()==(productid));
    }

    // Method to update the quantity of a product in the cart
    public void updateQuantity(int productid, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productid) {
                if (quantity > 0) {
                    item.setQuantity(quantity);
                } else {
                    // If quantity is 0 or less, remove the item from the cart
                    remove(productid);
                }
                return;
            }
        }
    }

	
    public float getTotalPrice() {
        float totalPrice = 0.0f;
        
        for (CartItem item : items) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }
        this.totalPrice = totalPrice;
        return totalPrice;
    }
	
	public List <CartItem> getItems(){
		return items;
	}

	public void setOffer(int offer_id) {
		this.offer = offer_id;
		
	}
	
	public int getOffer() {
		return offer;
	}



}


