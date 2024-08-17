package dao;

import java.sql.Connection;

import javax.servlet.ServletContext;

public class CartDAO extends DAO{
	
	Connection connection;

    public CartDAO(ServletContext context) {
        super(context);
    }
    
    public long getTotal() {
    	return 1;
    }
    
    public void add() {
    	
    }

}
