package model;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Cart {
	User owner;
	float total_price;
	Map <Product, Integer> items;
	private int offer_id;
	
	public Cart() {
		items =  new HashMap<>();

		
	}
	
	public void add(Product product) {
		
		if (items.containsKey(product)) {
			items.put(product, items.get(product) + 1);
			return;
        }
		
		items.put(product, 1);
	}
	
	public void remove(Product product) {
		//entirley remove
		items.remove(product);
		
	}
	
	public void updateQuantity(Product product, int x) {
		//can only be called on a product thats in the cart
		items.put(product, x);
		
	}
	
	public float getTotalPrice() {
	    float total = 0;
	    for (Map.Entry<Product, Integer> entry : items.entrySet()) {
	        Product product = entry.getKey();
	        int quantity = entry.getValue();
	        total += product.getPrice() * quantity;
	    }
	    return total;
	}

	public void setOffer(int offer_id) {
		this.offer_id = offer_id;
		
	}



}


