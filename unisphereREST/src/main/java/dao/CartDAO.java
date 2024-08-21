package dao;

import java.sql.Connection;

import javax.servlet.ServletContext;

public class CartDAO extends DAO{
	
	Connection connection;

    public CartDAO(ServletContext context) {
        super(context);
    }
    
    

}
