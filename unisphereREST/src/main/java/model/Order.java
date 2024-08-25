package model;

import java.sql.Date;

public class Order {
	
	private Cart cart;
	private Address address;
	private Payment payment;
	private Date ordered_on;
	
	public Order(Cart cart) {
		this.cart = cart;
	}
	
	// Getter and Setter for Cart
	public Cart getCart() {
		return cart;
	}
	
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	// Getter and Setter for Address
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	// Getter and Setter for Payment
	public Payment getPayment() {
		return payment;
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	// Getter and Setter for Ordered On
	public Date getOrderedOn() {
		return ordered_on;
	}
	
	public void setOrderedOn(Date ordered_on) {
		this.ordered_on = ordered_on;
	}
}
