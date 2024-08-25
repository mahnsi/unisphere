package dao;
import model.*;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;

public class OrderDAO extends DAO{
	
	Connection connection;

    public OrderDAO(ServletContext context) {
        super(context);
    }
    
    
    public List<Order> getAllOrders() {
    	return null;
    }
    
    public List<Order> getOrdersByUsername(String username) {
    	return null;
    }
    
    public Order getOrderbyId(int order_id) {
    	return null;
    	//dont implement for now
    }
    
    public void insert(Order order) {
    	//for place order
    }

}
