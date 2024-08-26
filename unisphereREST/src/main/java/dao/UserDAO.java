package dao;

import model.Address;
import model.Payment;
import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

public class UserDAO extends DAO {
	 private Connection connection;

    public UserDAO(ServletContext context) {
        super(context);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT username, email, firstname, lastname, is_admin, password FROM User";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                boolean isAdmin = rs.getBoolean("is_admin");
                String password = rs.getString("password");

                User user = new User(username, email, firstName, lastName, password);
                user.setIsAdmin(isAdmin);
                users.add(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    public User getUserByUsername(String username) {
        User user = null;
        String query = "SELECT * FROM User WHERE username = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String email = rs.getString("email");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    boolean isAdmin = rs.getBoolean("is_admin");
                    String password = rs.getString("password");

                    user = new User(username, email, firstName, lastName, password);
                    user.setIsAdmin(isAdmin);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return user;
    }

    public User getFullUserByUsername(String username) {
        User user = null;
        String query = "SELECT * FROM User u " +
                       "JOIN Address a ON u.address_id = a.id " +
                       "JOIN Payment p ON u.payment_id = p.id " +
                       "WHERE u.username = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String email = rs.getString("email");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    boolean isAdmin = rs.getBoolean("is_admin");
                    String password = rs.getString("password");

                    user = new User(username, email, firstName, lastName, password);
                    user.setIsAdmin(isAdmin);

                    Address address = new Address();
                    address.setStreetAddress(rs.getString("street_address"));
                    address.setFirstName(rs.getString("fname"));
                    address.setLastName(rs.getString("lname"));
                    address.setApartment(rs.getString("apt"));
                    address.setCity(rs.getString("city"));
                    address.setProvince(rs.getString("province"));
                    address.setPostalCode(rs.getString("postalcode"));
                    address.setCountry(rs.getString("country"));
                    user.setAddress(address);


                    Payment payment = new Payment();
                    payment.setCardHolderName(rs.getString("card_holder_name"));
                    payment.setCardNumber(rs.getString("card_number"));
                    payment.setExpirationDate(rs.getString("expiry"));
                    payment.setCvv(rs.getString("cvv"));
                    user.setPayment(payment);
                    
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return user;
    }

    public void update(String oldUsername, User updatedUser) {
        String query = "UPDATE User SET username = ?, firstname = ?, lastname = ? WHERE username = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, updatedUser.getUsername());
            stmt.setString(2, updatedUser.getFirstName());
            stmt.setString(3, updatedUser.getLastName());
            stmt.setString(4, oldUsername);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean insert(User user) {
        String query = "INSERT INTO User (username, email, firstname, lastname, is_admin, password) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setBoolean(5, user.getIsAdmin());
            stmt.setString(6, user.getPassword());
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void updateAddress(User user, Address updatedAddress) {
    	String query = "UPDATE Address SET street_address = ?, apt = ?, city = ?, province = ?, postalcode = ?, country = ? " +
                "WHERE id = (SELECT address_id FROM User WHERE username = ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, updatedAddress.getStreetAddress());
            stmt.setString(2, updatedAddress.getApartment());
            stmt.setString(3, updatedAddress.getCity());
            stmt.setString(4, updatedAddress.getProvince());
            stmt.setString(5, updatedAddress.getPostalCode());
            stmt.setString(6, updatedAddress.getCountry());
            stmt.setString(7, user.getUsername());
            stmt.executeUpdate();
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Address updated successfully. Rows affected: " + rowsAffected);
            } else {
                System.out.println("No address was updated.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updatePayment(User user, Payment updatedPayment) {
        String query = "UPDATE Payment SET card_holder_name = ?, card_number = ?, expiry = ?, cvv = ? "
        		+ "WHERE id = (select payment_id FROM User WHERE username =  ?)";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, updatedPayment.getCardHolderName());
            stmt.setString(2, updatedPayment.getCardNumber());
            stmt.setString(3, updatedPayment.getExpirationDate());
            stmt.setString(4, updatedPayment.getCvv());
            stmt.setString(5, user.getUsername());
            stmt.executeUpdate();
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Payment updated successfully. Rows affected: " + rowsAffected);
            } else {
                System.out.println("No payment was updated.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
<<<<<<< HEAD
    public boolean delete(String username) {
        String query = "DELETE FROM User WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

=======

    public void clearCart(String username) {
        String query = "DELETE FROM CART_ITEM WHERE added_by = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cart items cleared successfully. Rows affected: " + rowsAffected);
            } else {
                System.out.println("No items found in the cart for the specified user.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


>>>>>>> 2bbf824eb4dea0c72c349ee6473a581051e9f744
}
