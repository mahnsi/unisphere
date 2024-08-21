package dao;

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

        try {
            connection = getConnection();
            String query = "SELECT * FROM USER";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String first_name = rs.getString("firstname");
                String last_name = rs.getString("lastname");
                boolean isAdmin = rs.getBoolean("is_admin");
                String password = rs.getString("password");

                User user = new User(username, email, first_name, last_name, password);
                user.setIsAdmin(isAdmin);
                users.add(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return users;
    }

    public User getUserByUsername(String username) {
        User user = null;

        try {
            connection = getConnection();
            String query = "SELECT * FROM User WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                String first_name = rs.getString("firstname");
                String last_name = rs.getString("lastname");
                boolean isAdmin = rs.getBoolean("is_admin");
                String password = rs.getString("password");

                user = new User(username, email, first_name, last_name, password);
                user.setIsAdmin(isAdmin);
            }
            
            else {
            	//user doesnt exist
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return user;
    }

    public boolean insert(User user) {
        boolean isInserted = false;

        try {
            connection = getConnection();
            String query = "INSERT INTO User (username, email, firstname, lastname, is_admin, password) "+ 
            		"VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = connection.prepareStatement(query);
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setBoolean(5, user.getIsAdmin());
            stmt.setString(6, user.getPassword());
            System.out.println(user.getUsername() + " "+user.getPassword());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isInserted = true;
                System.out.println("user " +user.getUsername()+ " write to db success");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return isInserted;
    }

    public void update(String username, User updated_user) {
        try {
            connection = getConnection();
            String query = "UPDATE User SET email = ?, firstname = ?, lastname = ?, is_admin = ? WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, updated_user.getEmail());
            stmt.setString(2, updated_user.getFirstName());
            stmt.setString(3, updated_user.getLastName());
            stmt.setBoolean(4, updated_user.getIsAdmin());
            stmt.setString(5, username);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    public void delete(String username) {
        try {
            connection = getConnection();
            String query = "DELETE FROM User WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }
}
