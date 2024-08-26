package dao;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

public class OrderDAO extends DAO {
    
    Connection connection;

    public OrderDAO(ServletContext context) {
        super(context);
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String orderQuery = "SELECT * FROM Orders o " +
                            "JOIN Ordered_Item oi ON o.id = oi.order_id " +
                            "JOIN Product p ON oi.product_id = p.id " +
                            "JOIN Payment pay ON o.payment_id = pay.id " +
                            "JOIN Address addr ON o.address_id = addr.id";

        try (Connection connection = getConnection();
             PreparedStatement orderStmt = connection.prepareStatement(orderQuery);
             ResultSet orderRs = orderStmt.executeQuery()) {

            while (orderRs.next()) {
                int orderId = orderRs.getInt("order_id");
                Order order = new Order();
                order.setId(orderId);
                order.setCart(new Cart());  // Initialize the cart for this order

                // Populate the Payment and Address objects
                Payment payment = new Payment();
                payment.setId(orderRs.getInt("payment_id"));
                payment.setCardHolderName(orderRs.getString("card_holder_name"));
                payment.setExpirationDate(orderRs.getString("expiry"));
                payment.setCardNumber(orderRs.getString("card_number"));
                payment.setCvv(orderRs.getString("cvv"));
                
                order.setPayment(payment);

                Address address = new Address();
                address.setId(orderRs.getInt("address_id"));
                address.setStreetAddress(orderRs.getString("street_address"));
                address.setCity(orderRs.getString("city"));
                address.setPostalCode(orderRs.getString("postalcode"));
                address.setProvince(orderRs.getString("province"));
                address.setCountry(orderRs.getString("country"));
                address.setFirstName(orderRs.getString("fname"));
                address.setLastName(orderRs.getString("lname"));
                
                order.setAddress(address);

                // Populate the cart with products
                Product product = new Product();
                product.setId(orderRs.getInt("product_id"));
                product.setTitle(orderRs.getString("title"));
                product.setPrice(orderRs.getFloat("price"));

                order.getCart().add(product);  // Add product to the order's cart

                orders.add(order);  // Add order to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Proper error handling should be implemented
        }

        return orders;
    }

    public List<Order> getOrdersByUsername(String username) {
        List<Order> orders = new ArrayList<>();
        String orderQuery = "SELECT * " +
                            "FROM Orders o " +
                            "JOIN Ordered_Item oi ON o.id = oi.order_id " +
                            "JOIN Product p ON oi.product_id = p.id " +
                            "JOIN Payment pay ON o.payment_id = pay.id " +
                            "JOIN Address addr ON o.address_id = addr.id " +
                            "WHERE o.created_by = ?";

        try (Connection connection = getConnection();
             PreparedStatement orderStmt = connection.prepareStatement(orderQuery)) {
             
            orderStmt.setString(1, username);  // Set the username parameter
            
            try (ResultSet orderRs = orderStmt.executeQuery()) {
                while (orderRs.next()) {
                    int orderId = orderRs.getInt("order_id");
                    Order order = new Order();
                    order.setId(orderId);
                    order.setCart(new Cart());  // Initialize the cart for this order

                    // Populate the Payment and Address objects
                    Payment payment = new Payment();
                    payment.setId(orderRs.getInt("payment_id"));
                    payment.setCardHolderName(orderRs.getString("card_holder_name"));
                    payment.setExpirationDate(orderRs.getString("expiry"));
                    payment.setCardNumber(orderRs.getString("card_number"));
                    payment.setCvv(orderRs.getString("cvv"));
                    
                    order.setPayment(payment);

                    Address address = new Address();
                    address.setId(orderRs.getInt("address_id"));
                    address.setStreetAddress(orderRs.getString("street_address"));
                    address.setCity(orderRs.getString("city"));
                    address.setPostalCode(orderRs.getString("postalcode"));
                    address.setProvince(orderRs.getString("province"));
                    address.setCountry(orderRs.getString("country"));
                    address.setFirstName(orderRs.getString("fname"));
                    address.setLastName(orderRs.getString("lname"));
                    
                    order.setAddress(address);

                    // Populate the cart with products
                    Product product = new Product();
                    product.setId(orderRs.getInt("product_id"));
                    product.setTitle(orderRs.getString("title"));
                    product.setPrice(orderRs.getFloat("price"));

                    order.getCart().add(product);  // Add product to the order's cart

                    orders.add(order);  // Add order to the list
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Proper error handling should be implemented
        }
        return orders;
    }

    public Order getOrderById(int orderId) {
        Order order = null;
        String orderQuery = "SELECT * " +
                            "FROM Orders o " +
                            "JOIN Ordered_Item oi ON o.id = oi.order_id " +
                            "JOIN Product p ON oi.product_id = p.id " +
                            "JOIN Payment pay ON o.payment_id = pay.id " +
                            "JOIN Address addr ON o.address_id = addr.id " +
                            "WHERE o.id = ?";

        try (Connection connection = getConnection();
             PreparedStatement orderStmt = connection.prepareStatement(orderQuery)) {
             
            orderStmt.setInt(1, orderId);  // Set the order ID parameter

            try (ResultSet orderRs = orderStmt.executeQuery()) {
                if (orderRs.next()) {  // Assuming there's only one order per ID
                    order = new Order();
                    order.setId(orderId);
                    order.setCart(new Cart());  // Initialize the cart for this order

                    // Populate the Payment and Address objects
                    Payment payment = new Payment();
                    payment.setId(orderRs.getInt("payment_id"));
                    payment.setCardHolderName(orderRs.getString("card_holder_name"));
                    payment.setExpirationDate(orderRs.getString("expiry"));
                    payment.setCardNumber(orderRs.getString("card_number"));
                    payment.setCvv(orderRs.getString("cvv"));
                    
                    order.setPayment(payment);

                    Address address = new Address();
                    address.setId(orderRs.getInt("address_id"));
                    address.setStreetAddress(orderRs.getString("street_address"));
                    address.setCity(orderRs.getString("city"));
                    address.setPostalCode(orderRs.getString("postalcode"));
                    address.setProvince(orderRs.getString("province"));
                    address.setCountry(orderRs.getString("country"));
                    address.setFirstName(orderRs.getString("fname"));
                    address.setLastName(orderRs.getString("lname"));
                    
                    order.setAddress(address);

                    // Populate the cart with products
                    do {
                        Product product = new Product();
                        product.setId(orderRs.getInt("product_id"));
                        product.setTitle(orderRs.getString("title"));
                        product.setPrice(orderRs.getFloat("price"));

                        order.getCart().add(product);  // Add product to the order's cart
                    } while (orderRs.next());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Proper error handling should be implemented
        }
        return order;
    }

    public void insert(Order order) {
        String insertOrderQuery = "INSERT INTO Orders (id, created_by, payment_id, address_id, order_number, total) VALUES (?, ?, ?, ?, ?, ?)";
        String insertOrderedItemQuery = "INSERT INTO Ordered_Item (order_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement orderStmt = connection.prepareStatement(insertOrderQuery);
             PreparedStatement itemStmt = connection.prepareStatement(insertOrderedItemQuery)) {

            // Insert the order
            orderStmt.setInt(1, order.getId());
            orderStmt.setString(2, order.getCreatedBy());
            orderStmt.setInt(3, order.getPayment().getId());
            orderStmt.setInt(4, order.getAddress().getId());
            orderStmt.setString(5, order.getOrderNumber()); // Set the random order number
            orderStmt.setFloat(6, order.getTotal());
            orderStmt.executeUpdate();

            // Insert ordered items
            for (CartItem item : order.getCart().getItems()) {
                itemStmt.setInt(1, order.getId());
                itemStmt.setInt(2, item.getProduct().getId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.addBatch();
            }
            itemStmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
