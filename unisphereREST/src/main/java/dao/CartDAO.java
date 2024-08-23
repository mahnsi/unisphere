package dao;

import java.sql.Connection;

import javax.servlet.ServletContext;

import model.Cart;

public class CartDAO extends DAO{
	
	Connection connection;

    public CartDAO(ServletContext context) {
        super(context);
    }
    
    public Cart getCartByUsername(String username) {
    	return null;
    }

	public void updateCart(Cart cart) {
		// TODO Auto-generated method stub
		
		//delete or update quantity of an item
		
	}

    
    

}
