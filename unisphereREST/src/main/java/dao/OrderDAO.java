package dao;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        System.out.println("DAO insert order called");
        
        String insertPaymentQuery = "INSERT INTO Payment (card_holder_name, expiry, card_number, cvv) VALUES (?, ?, ?, ?)";
        String insertAddressQuery = "INSERT INTO Address (street_address, city, postalcode, province, country, fname, lname) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertOrderQuery = "INSERT INTO Orders (id, created_by, payment_id, address_id, total) VALUES (?, ?, ?, ?, ?)";
        String insertOrderedItemQuery = "INSERT INTO Ordered_Item (order_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement paymentStmt = connection.prepareStatement(insertPaymentQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement addressStmt = connection.prepareStatement(insertAddressQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement orderStmt = connection.prepareStatement(insertOrderQuery);
             PreparedStatement itemStmt = connection.prepareStatement(insertOrderedItemQuery)) {

            // Insert Payment
            Payment payment = order.getPayment();
            paymentStmt.setString(1, payment.getCardHolderName());
            paymentStmt.setString(2, payment.getExpirationDate());
            paymentStmt.setString(3, payment.getCardNumber());
            paymentStmt.setString(4, payment.getCvv());
            paymentStmt.executeUpdate();

            // Retrieve the generated payment ID
            try (ResultSet paymentRs = paymentStmt.getGeneratedKeys()) {
                if (paymentRs.next()) {
                    int paymentId = paymentRs.getInt(1);
                    System.out.println("generated payment id:"+ paymentId);
                    payment.setId(paymentId); // Set the ID to the payment object
                } else {
                    throw new SQLException("Inserting payment failed, no ID obtained.");
                }
            }

            // Insert Address
            Address address = order.getAddress();
            addressStmt.setString(1, address.getStreetAddress());
            addressStmt.setString(2, address.getCity());
            addressStmt.setString(3, address.getPostalCode());
            addressStmt.setString(4, address.getProvince());
            addressStmt.setString(5, address.getCountry());
            addressStmt.setString(6, address.getFirstName());
            addressStmt.setString(7, address.getLastName());
            addressStmt.executeUpdate();

            // Retrieve the generated address ID
            try (ResultSet addressRs = addressStmt.getGeneratedKeys()) {
                if (addressRs.next()) {
                    int addressId = addressRs.getInt(1);
                    System.out.println("generated address id:"+ addressId);
                    address.setId(addressId); // Set the ID to the address object
                } else {
                    throw new SQLException("Inserting address failed, no ID obtained.");
                }
            }
            
            // Insert the Order with payment and address IDs
            orderStmt.setInt(1, order.getId());
            orderStmt.setString(2, order.getCreatedBy());
            System.out.println("createddd by: " + order.getCreatedBy());
            orderStmt.setInt(3, payment.getId());  // Use the generated payment ID
            orderStmt.setInt(4, address.getId());  // Use the generated address ID
            orderStmt.setFloat(5, order.getTotal());
            int orderRowsAffected = orderStmt.executeUpdate();
            
            int orderId;
            try (ResultSet orderRs = orderStmt.getGeneratedKeys()) {
                if (orderRs.next()) {
                    orderId = orderRs.getInt(1);
                    System.out.println("Generated order ID: " + orderId);
                    order.setId(orderId); // Set the ID to the order object
                } else {
                    throw new SQLException("Inserting order failed, no ID obtained.");
                }
            }
            
            System.out.println("Order insert affected " + orderRowsAffected + " row(s)");

            if (orderRowsAffected == 0) {
                throw new SQLException("Inserting order failed, no rows affected.");
            }

            // Insert ordered items
            for (CartItem item : order.getCart().getItems()) {
                itemStmt.setInt(1, order.getId());
                itemStmt.setInt(2, item.getProduct().getId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.addBatch();
            }
            int[] itemRowsAffected = itemStmt.executeBatch();
            System.out.println("Ordered items insert affected " + itemRowsAffected.length + " row(s)");

            if (itemRowsAffected.length == 0) {
                throw new SQLException("Inserting ordered items failed, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
