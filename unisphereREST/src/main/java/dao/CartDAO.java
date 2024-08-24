package dao;

import java.sql.Connection;
import java.sql.*;

import javax.servlet.ServletContext;

import model.Cart;
import model.Product;

public class CartDAO extends DAO{
	
	Connection connection;

    public CartDAO(ServletContext context) {
        super(context);
    }
    
    public Cart getCartByUsername(String username) {
        Cart cart = new Cart();

        try {
            String query = "SELECT p.id, p.name, p.price, ci.quantity, p.description, c.offer_id " +
                           "FROM cart_item ci " +
                           "JOIN product p ON ci.product_id = p.id " +
                           "JOIN cart c ON ci.added_by = c.owner " +
                           "JOIN user u ON c.username = u.username " +
                           "WHERE u.username = ?";
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            int offer_id = -1;
            while (rs.next()) {
                int productId = rs.getInt("id");
                String title = rs.getString("title");
                String desc = rs.getString("desc");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                offer_id = rs.getInt("offer_id");

                Product product = new Product();
                product.setId(productId);
                product.setTitle(title);
                product.setDescription(username);
                product.setPrice(price);
                cart.add(product);
                cart.updateQuantity(product, quantity);
            }
            cart.setOffer(offer_id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return cart;
    }


    public void updateQuantity(String username, int productid, int x) {
        try {
            connection = getConnection();
            String query = "UPDATE CART_ITEM SET quantity = ? WHERE added_by = ? AND product_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, x);
            statement.setString(2, username);
            statement.setInt(3, productid);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }



	
    public void addToCart(String username, Product product) {
        try {
            connection = getConnection();

            // Check if the product is already in the cart
            String checkQuery = "SELECT quantity FROM CART_ITEM WHERE added_by = ? AND product_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            checkStmt.setInt(2, product.getId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Product already in the cart, update quantity
                int currentQuantity = rs.getInt("quantity");
                String updateQuery = "UPDATE CART_ITEM SET quantity = ? WHERE added_by = ? AND product_id = ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                updateStmt.setInt(1, currentQuantity + 1); // Increase quantity by 1
                updateStmt.setString(2, username);
                updateStmt.setInt(3, product.getId());
                updateStmt.executeUpdate();
            } else {
                // Product not in the cart, insert new entry
                String insertQuery = "INSERT INTO CART_ITEM (added_by, product_id, quantity) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                insertStmt.setString(1, username);
                insertStmt.setInt(2, product.getId());
                insertStmt.setInt(3, 1); // Start with a quantity of 1
                insertStmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }


    public void removeFromCart(String username, Product product) {
        try {
            connection = getConnection();
            String query = "DELETE FROM CART_ITEM WHERE added_by = ? AND product_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setInt(2, product.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }




    
    

}
