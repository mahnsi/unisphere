package model;

import java.sql.Date;

public class Order {
	
	private int id;
	private Cart cart;
	private Address address;
	private Payment payment;
	private Date ordered_on;
	private String orderNumber;  
    private String createdBy;  // New field to store the username of the user who created the order
    private float total;  // Assuming there's a total field as well

    // Constructors, getters, and setters...

	public Order(Cart cart) {
		this.cart = cart;
	}
	
	public Order() {
		
	}

    // Getter and Setter for createdBy
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    // Other getters and setters...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Date getOrderedOn() {
        return ordered_on;
    }

    public void setOrderedOn(Date ordered_on) {
        this.ordered_on = ordered_on;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
