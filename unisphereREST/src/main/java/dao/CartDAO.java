package dao;

import java.sql.Connection;
import java.sql.*;

import javax.servlet.ServletContext;

import model.Cart;
import model.CartItem;
import model.Product;

public class CartDAO extends DAO{
	
	Connection connection;

    public CartDAO(ServletContext context) {
        super(context);
    }
    
    public Cart getCartByUsername(String username) {
    	System.out.println("DAO getcartbyuser called");
        Cart cart = new Cart();

        try {
            String query = "SELECT * from cart_item join product p on product_id = p.id where added_by = '" + username + "'";
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            int offer_id = -1;
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String title = rs.getString("title");
                String desc = rs.getString("description");
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                int sold = rs.getInt("purchase_count");
                int stock = rs.getInt("inventory_count");
                int subcategory = rs.getInt("subcategory_id");
                
                //offer_id = rs.getInt("offer_id");

                Product product = new Product();
                product.setId(productId);
                product.setTitle(title);
                product.setDescription(desc);
                product.setPrice(price);
                product.setStock(stock); 
                product.setSold(sold); 
                product.setSubCategory(subcategory);
                
                cart.add(product);
                cart.updateQuantity(product.getId(), quantity);
            }
            //cart.setOffer(offer_id);
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
            System.out.println("addtocart dao called");
            // Check if the product is already in the cart
            String checkQuery = "SELECT quantity FROM CART_ITEM WHERE added_by = ? AND product_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            checkStmt.setInt(2, product.getId());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();

            if (rs.next()) {
                // Product already in the cart, update quantity
            	System.out.println("already in cart");
                int currentQuantity = rs.getInt("quantity");
                String updateQuery = "UPDATE CART_ITEM SET quantity = ? WHERE added_by = ? AND product_id = ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                updateStmt.setInt(1, currentQuantity + 1); // Increase quantity by 1
                updateStmt.setString(2, username);
                updateStmt.setInt(3, product.getId());
                updateStmt.executeUpdate();
            } else {
                // Product not in the cart, insert new entry
            	System.out.println("dao add new entry to cart");
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


    public void removeFromCart(String username, int productid) {
        System.out.println("DAO removeFromCart called");
        int rowsAffected = 0;
        try {
            connection = getConnection();
            String query = "DELETE FROM CART_ITEM WHERE added_by = ? AND product_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setInt(2, productid);
            rowsAffected = statement.executeUpdate(); // Store the number of rows affected
            System.out.println("Rows affected: " + rowsAffected);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }
    
    public void setCartForUser(String username, Cart cart) {
        try {
            connection = getConnection();

            // Clear the existing cart items for the user
            String clearQuery = "DELETE FROM CART_ITEM WHERE added_by = ?";
            PreparedStatement clearStmt = connection.prepareStatement(clearQuery);
            clearStmt.setString(1, username);
            clearStmt.executeUpdate();

            // Insert new cart items for the user
            String insertQuery = "INSERT INTO CART_ITEM (added_by, product_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);

            for (CartItem cartitem : cart.getItems()) {
                insertStmt.setString(1, username);
                insertStmt.setInt(2, cartitem.getProduct().getId());
                insertStmt.setInt(3, cartitem.getQuantity());
                insertStmt.addBatch();
            }

            // Execute the batch of insert statements
            insertStmt.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }


    

}
