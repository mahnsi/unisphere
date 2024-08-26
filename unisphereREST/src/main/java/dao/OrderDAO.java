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
        String itemQuery = "SELECT * FROM Ordered_Item oi JOIN Product p ON oi.product_id = p.id WHERE oi.order_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement orderStmt = connection.prepareStatement(orderQuery);
             ResultSet orderRs = orderStmt.executeQuery()) {

            while (orderRs.next()) {
                int orderId = orderRs.getInt("order_id");
                Order order = new Order();
                order.setId(orderId);
                order.setCart(new Cart());  // Initialize the cart for this order

                // Retrieve items for this order
                try (PreparedStatement itemStmt = connection.prepareStatement(itemQuery)) {
                    itemStmt.setInt(1, orderId);
                    try (ResultSet itemRs = itemStmt.executeQuery()) {
                        while (itemRs.next()) {
                            Product product = new Product();
                            product.setId(itemRs.getInt("product_id"));
                            product.setTitle(itemRs.getString("title"));
                            product.setPrice(itemRs.getFloat("price"));

                            order.getCart().add(product);  // Add product to the order's cart
                        }
                    }
                }

                orders.add(order);  // Add order to the list
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
