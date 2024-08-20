package dao;

import java.sql.Connection;

import javax.servlet.ServletContext;

public class OrderDAO extends DAO{
	
	Connection connection;

    public OrderDAO(ServletContext context) {
        super(context);
    }
    
    public long getTotal() {
    	return 1;
    }
    
    public String getId() {
    	return null;
    }
    
    public void insert() {
    	
    }

}
