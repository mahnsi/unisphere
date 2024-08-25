package dao;

import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletContext;

public class UserDAO extends DAO {

    private Connection connection;

    public UserDAO(ServletContext context) {
        super(context);
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
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                boolean isAdmin = rs.getBoolean("is_admin");
                String password = rs.getString("password");

                user = new User(username, email, firstName, lastName, password);
                user.setIsAdmin(isAdmin);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return user;
    }

    public void update(String oldUsername, User updatedUser) {
        try {
            connection = getConnection();
            String query = "UPDATE User SET username = ?, firstname = ?, lastname = ? WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, updatedUser.getUsername());
            stmt.setString(2, updatedUser.getFirstName());
            stmt.setString(3, updatedUser.getLastName());
            stmt.setString(4, oldUsername);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }
}
