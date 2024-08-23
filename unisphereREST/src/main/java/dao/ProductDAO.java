package dao;

import model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class ProductDAO extends DAO {

    private Connection connection;

    public ProductDAO(ServletContext context) {
        super(context);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try {
            connection = getConnection();
            String query = "SELECT * from PRODUCT";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                float price = rs.getFloat("price");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int categoryId = rs.getInt("subcategory_id");

                Product product = new Product();
                product.setId(id);
                product.setPrice(price);
                product.setTitle(title);
                product.setDescription(description);
                product.setSubCategory(categoryId); // Store category ID directly

                products.add(product);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return products;
    }
    
    public List<Product> getProductsByCategory(int category_id){
    	List<Product> products = new ArrayList<>();

        try {
            connection = getConnection();
            String query = "SELECT * FROM Product p " +
                    "JOIN Subcategory s ON p.subcategory_id = s.id " +
                    "WHERE s.category_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, category_id); 
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                float price = rs.getFloat("price");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int subcategoryId = rs.getInt("subcategory_id");

                Product product = new Product();
                product.setId(id);
                product.setPrice(price);
                product.setTitle(title);
                product.setDescription(description);
                product.setSubCategory(subcategoryId);

                products.add(product);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return products;
    }
    
    public List<Product> getProductsBySubCategory(String category){
    	System.out.println("getProductsBySubCategory called");
    	List<Product> products = new ArrayList<>();

        try {
            connection = getConnection();
            String query = "SELECT p.* FROM PRODUCT p JOIN SUBCATEGORY s ON p.subcategory_id = s.id WHERE s.desc = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, category); // subcategoryName is the name of the subcategory you are searching for
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                float price = rs.getFloat("price");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int subcategoryId = rs.getInt("subcategory_id");

                Product product = new Product();
                product.setId(id);
                product.setPrice(price);
                product.setTitle(title);
                product.setDescription(description);
                product.setSubCategory(subcategoryId); // Store category ID directly

                products.add(product);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        System.out.println(products);
        return products;
    	
    }

    public Product getProductById(int id) {
        Product product = null;

        try {
            connection = getConnection();
            String query = "SELECT * FROM Product WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                float price = rs.getFloat("price");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int subcategoryId = rs.getInt("subcategory_id");

                product = new Product();
                product.setId(id);
                product.setPrice(price);
                product.setTitle(title);
                product.setDescription(description);
                product.setSubCategory(subcategoryId); // Store category ID directly
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return product;
    }

    public boolean insert(Product product) {
        boolean isInserted = false;

        try {
            connection = getConnection();
            String query = "INSERT INTO Product (id, price, title, description, subcategory_id) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement stmt = connection.prepareStatement(query);
            
            stmt.setInt(1, product.getId());
            stmt.setFloat(2, product.getPrice());
            stmt.setString(3, product.getTitle());
            stmt.setString(4, product.getDescription());
            stmt.setInt(5, product.getSubCategoryId()); // Use category ID directly

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isInserted = true;
                System.out.println("Product " + product.getTitle() + " inserted into the database successfully.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return isInserted;
    }

    public void update(int id, Product updatedProduct) {
        try {
            connection = getConnection();
            String query = "UPDATE Product SET price = ?, title = ?, description = ?, category_id = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setFloat(1, updatedProduct.getPrice());
            stmt.setString(2, updatedProduct.getTitle());
            stmt.setString(3, updatedProduct.getDescription());
            stmt.setInt(4, updatedProduct.getCategoryId()); // Use category ID directly
            stmt.setInt(5, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    public void delete(int id) {
        try {
            connection = getConnection();
            String query = "DELETE FROM Product WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

	public List<Product> getFeaturedProducts() {
		List<Product> products = new ArrayList<>();

        try {
            connection = getConnection();//expiration date on featured
            String query = "select * from product join featured_item on product.id = featured_item.product_id";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                float price = rs.getFloat("price");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int categoryId = rs.getInt("category_id");

                Product product = new Product();
                product.setId(id);
                product.setPrice(price);
                product.setTitle(title);
                product.setDescription(description);
                product.setCategory(categoryId); // Store category ID directly

                products.add(product);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return products;
	}
	
	public List<String> getSubcategoriesByCategory(String category) {
	    List<String> subcategoryNames = new ArrayList<>();
	    System.out.println("getSubcategoriesByCategory called");

	    try {
	        connection = getConnection();
	        String query = "SELECT s.desc " +
                    "FROM subcategory s " +
                    "JOIN category c ON s.category_id = c.id " +
                    "WHERE c.name = ?";
	        PreparedStatement stmt = connection.prepareStatement(query);
	        stmt.setString(1, category);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String name = rs.getString("desc");
	            subcategoryNames.add(name);
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    } finally {
	        closeConnection(connection);
	    }

	    System.out.println(subcategoryNames);
	    return subcategoryNames;
	}

	public List<Product> getProductsByKeyword(String key) {
		List<Product> products = new ArrayList<>();

        try {
            connection = getConnection();//expiration date on featured
            String query = "select * from product where title like ? or description like ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, key);
            stmt.setString(2, key);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                float price = rs.getFloat("price");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int categoryId = rs.getInt("category_id");

                Product product = new Product();
                product.setId(id);
                product.setPrice(price);
                product.setTitle(title);
                product.setDescription(description);
                product.setCategory(categoryId); // Store category ID directly

                products.add(product);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return products;
	}


}
