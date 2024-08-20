package dao;
import model.*;

import java.sql.Connection;

import javax.servlet.ServletContext;

public class OrderDAO extends DAO{
	
	Connection connection;

    public OrderDAO(ServletContext context) {
        super(context);
    }
    
    
    public Order getOrderbyId() {
    	return null;
    }
    
    public void insert() {
    	
    }

}
