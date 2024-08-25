package dao;

import java.sql.Connection;

import javax.servlet.ServletContext;

import model.Product;
import model.Wishlist;

public class WishlistDAO extends DAO{
	private Connection connection;

	public WishlistDAO(ServletContext context) {
        super(context);
    }

	public Wishlist getWishlistByUsername(String uname) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void insertIntoWishlist(Product product, String username) {
		
	}


}
