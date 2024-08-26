package dao;
import ds.*;
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
                int stock = rs.getInt("inventory_count");
                int sold = rs.getInt("purchase_count");

                Product product = new Product();
                product.setId(id);
                product.setPrice(price);
                product.setTitle(title);
                product.setDescription(description);
                product.setSubCategory(categoryId); // Store category ID directly
                product.setStock(stock);
                product.setSold(sold);

                products.add(product);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return products;
    }
    
    public List<Product> getProductsByCategory(String category){
    	List<Product> products = new ArrayList<>();

        try {
            connection = getConnection();
            String query = "SELECT * FROM Product p " +
                    "JOIN Subcategory s ON p.subcategory_id = s.id " +
                    "JOIN Category c ON c.id = s.category_id "+
                    "WHERE c.name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, category); 
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
                int stock = rs.getInt("inventory_count");

                product = new Product();
                product.setId(id);
                product.setPrice(price);
                product.setTitle(title);
                product.setDescription(description);
                product.setSubCategory(subcategoryId); // Store category ID directly
                product.setStock(stock);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return product;
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
	
	public List<Tuple> getSubcategoriesByCategory(String category) {
	    List<Tuple> subcategoryNames = new ArrayList<>();
	    System.out.println("getSubcategoriesByCategory called");

	    try {
	        connection = getConnection();
	        String query = "SELECT s.id, s.desc " +
                    "FROM subcategory s " +
                    "JOIN category c ON s.category_id = c.id " +
                    "WHERE c.name = ?";
	        PreparedStatement stmt = connection.prepareStatement(query);
	        stmt.setString(1, category);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	        	int id = rs.getInt("id");
	            String name = rs.getString("desc");
	            subcategoryNames.add(new Tuple(id, name));
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
		System.out.println("dao getProductsByKeyword" + key);
		List<Product> products = new ArrayList<>();

        try {
            connection = getConnection();//expiration date on featured
            String query = "select * from product where title like ? or description like ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + key + "%");
            stmt.setString(2, "%" + key + "%");

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
                System.out.println("dao size=" + products.size());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return products;
	}
	
	public void addProduct(Product product) throws SQLException {
		System.out.println("addProduct DAO called");
        String sql = "INSERT INTO product (title, description, subcategory_id, price, inventory_count, purchase_count) VALUES (?, ?, ?, ?, ?, ?)";
        connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getTitle());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getSubCategory());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getStock());
            statement.setInt(6, product.getSold());
            

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

            // Optionally, retrieve the generated ID and set it to the product object
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
        }
    }

	public List<String> getAllCategories() {
		List<String> subcategoryNames = new ArrayList<>();
	    System.out.println("DAO getAllCategories called");

	    try {
	        connection = getConnection();
	        String query = "SELECT * from category";
	        PreparedStatement stmt = connection.prepareStatement(query);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String name = rs.getString("name");
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

	public boolean updateProductQuantity(int id, int quantity) {
	    // Define the SQL update query
		System.out.println("DAO updateInventory called");
	    String sql = "UPDATE product SET inventory_count = ? WHERE id = ?";

	    // Use try-with-resources to ensure resources are closed
	    try (Connection connection = getConnection(); // Method to obtain a database connection
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        // Set parameters for the SQL query
	        statement.setInt(1, quantity); // Set the quantity
	        statement.setInt(2, id); // Set the product ID

	        // Execute the update
	        int rowsAffected = statement.executeUpdate();
	        System.out.println(rowsAffected);
	        // Check if the update was successful
	        return rowsAffected > 0; // Return true if at least one row was updated, otherwise false

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("dao fail");
	        return false;
	    }
	}


}
