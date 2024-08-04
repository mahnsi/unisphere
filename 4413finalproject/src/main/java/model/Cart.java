package model;

import java.util.List;

public class Cart {
	User owner;
	float total_price;
	List <Product> items;
	
	public Cart() {
		
	}
	
	public void add(Product product) {
		items.add(product);
	}

}


