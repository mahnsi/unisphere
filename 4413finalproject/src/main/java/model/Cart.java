package model;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Cart {
	User owner;
	float total_price;
	Map <Product, Integer> items;
	
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
		items.put(product, x);
		
	}

}


