package dao;

import model.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

public class UserDAO extends DAO {
	
	Connection connection;

    public UserDAO(ServletContext context) {
        super(context);
    }

    public List<User> getAllUsers() {
    	List<User> users = new ArrayList<>();

    	try {
            connection = getConnection();
           //logic for fetching a user by username from db
       } 
       
       catch (SQLException ex) {
           ex.printStackTrace();
       } 
       finally {
           closeConnection(connection);
       }
    	
    	return users;
    }

    public User getUserByUsername(String username) {
    	
    	User user = new User("", "", "", "");

        try {
             connection = getConnection();
            //logic for fetching a user by username from db
        } 
        
        catch (SQLException ex) {
            ex.printStackTrace();
        } 
        finally {
            closeConnection(connection);
        }
        
		return user;
    }
    public void update(String username, User updated_user) {
    	
    }
    
    public void insert(User user) {
       
    }

    public void delete(String username) {
        
    }
}
