package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;

import model.Product;
import model.Wishlist;

public class WishlistDAO extends DAO {
    private Connection connection;

    public WishlistDAO(ServletContext context) {
        super(context);
    }

    public Wishlist getWishlistByUsername(String username) {
        Wishlist wishlist = new Wishlist();
        try {
            String query = "SELECT p.* FROM WISHLIST_ITEM wi JOIN PRODUCTS p ON wi.product_id = p.id WHERE wi.saved_by = ?";
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int productId = rs.getInt("id");
                String title = rs.getString("title");
                String desc = rs.getString("description");
                float price = rs.getFloat("price");
                int stock = rs.getInt("inventory_count");
                int subcategory = rs.getInt("subcategory_id");

                Product product = new Product();
                product.setId(productId);
                product.setTitle(title);
                product.setDescription(desc);
                product.setPrice(price);
                product.setStock(stock);
                product.setSubCategory(subcategory);

                wishlist.add(product);
                System.out.println("Product retrieved from wishlist: " + product.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return wishlist;
    }

    public void insertIntoWishlist(Product product, String username) {
        try {
            connection = getConnection();

            String checkQuery = "SELECT COUNT(*) FROM WISHLIST_ITEM WHERE saved_by = ? AND product_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            checkStmt.setInt(2, product.getId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Product already in the wishlist");
                return;  // Product already in the wishlist
            }

            String insertQuery = "INSERT INTO WISHLIST_ITEM (saved_by, product_id) VALUES (?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, username);
            insertStmt.setInt(2, product.getId());
            int rowsInserted = insertStmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Product added to wishlist: " + product.getTitle());
            } else {
                System.out.println("Failed to add product to wishlist: " + product.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    public void removeFromWishlist(String username, int productId) {
        try {
            connection = getConnection();
            String query = "DELETE FROM WISHLIST_ITEM WHERE saved_by = ? AND product_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setInt(2, productId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Product removed from wishlist: " + productId);
            } else {
                System.out.println("Failed to remove product from wishlist: " + productId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }
}
