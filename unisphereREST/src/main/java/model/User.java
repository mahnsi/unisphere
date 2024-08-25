package model;

public class User {
	String username;
	String firstName;
	String lastName;
	String email;
	String password;
	Address address;
	Payment payment;
	boolean isAdmin;
	Cart cart;
	
	
	public User(String username, String email, String first_name, String last_name, String password) {
		this.username = username;
		this.email = email;
		this.firstName = first_name;
		this.lastName = last_name;
		isAdmin = false;
		this.password = password;
	}
	
	public User() {
    }


	public String getUsername() {
		return username;
	}
	

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }
    
    public String getPassword() {
    	return password;
    	
    }
    
    public Cart getCart() {
    	return cart;
    }
    
    public Payment getPayment() {
    	return payment;
    }
    
    public Address getAddress() {
    	return address;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public void setCart(Cart cart) {
    	this.cart = cart;
    }
    
    public void setPayment(Payment payment) {
    	this.payment = payment;
    }
    
    public void setAddress(Address address) {
    	this.address = address;
    }
}

