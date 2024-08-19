package model;

public class User {
	String username;
	String first_name;
	String last_name;
	String email; //use email regex from lab 1 or 2 when accepting	no
	//cart ??
	//wishlist ??
	//boolean isAdmin ??
	
	
	public User(String username, String email, String first_name, String last_name) {
		this.username = username;
		this.email = email;
		this.first_name = first_name;
		this.last_name = last_name;
	}

	

}

