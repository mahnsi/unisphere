package dao;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

public class OrderDAO extends DAO{
	
	Connection connection;

    public OrderDAO(ServletContext context) {
        super(context);
    }
    
    
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders o join ordered_item oi"+
        "on o.id = oi.order_id";
        
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                Order order = new Order();
                // Assuming Order class has appropriate setters
                //order.setOrderId(rs.getInt("order_id"));
                //order.setUsername(rs.getString("username"));
                //order.setOrderDate(rs.getDate("order_date"));
                // Add other fields as necessary
                
                orders.add(order);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();  // Proper error handling should be implemented
        }
        
        return orders;
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
