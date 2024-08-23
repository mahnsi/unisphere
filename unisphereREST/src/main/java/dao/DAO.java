package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;

public abstract class DAO {
    
    protected ServletContext context;
    
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public DAO(ServletContext context) {
        this.context = context;
        
    }
    
    protected Connection getConnection() throws SQLException {
        String path = context.getRealPath("/UniSphere.db");
        //System.out.println(path);
        return DriverManager.getConnection("jdbc:sqlite:" + path);
    }

    protected void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
