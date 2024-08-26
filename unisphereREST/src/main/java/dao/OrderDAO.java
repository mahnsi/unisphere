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
        String orderQuery = "SELECT * FROM Orders";
        String itemQuery = "SELECT * FROM Ordered_Item oi JOIN Products p ON oi.product_id = p.id WHERE oi.order_id = ?";

        try (PreparedStatement orderStmt = connection.prepareStatement(orderQuery);
             ResultSet orderRs = orderStmt.executeQuery()) {
            
            while (orderRs.next()) {
                int orderId = orderRs.getInt("id");
                Order order = new Order();
                order.setId(orderId);
                //order.setItems(new ArrayList<>());
                
                // Retrieve items for this order
                try (PreparedStatement itemStmt = connection.prepareStatement(itemQuery)) {
                    itemStmt.setInt(1, orderId);
                    try (ResultSet itemRs = itemStmt.executeQuery()) {
                        while (itemRs.next()) {
                            CartItem item = new CartItem();
                            
                            Product product = new Product();
                            product.setId(itemRs.getInt("product_id"));
                            product.setTitle(itemRs.getString("product_name"));
                            product.setPrice(itemRs.getFloat("product_price"));
                            
                            item.setProduct(product);
                            item.setQuantity(itemRs.getInt("quantity"));
                            
                            //order.getCart().add(item);
                        }
                    }
                }
                
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
