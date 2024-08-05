package model;

import java.sql.Date;

public class Order {
	
	private Cart ordered_cart;
	public Date ordered_on;
	
	public Order(Cart cart) {
		ordered_cart = cart;
	}
	
	public Cart getCart() {
		return ordered_cart;
	}
	
	//no public access to ordered_cart
	
 
}
