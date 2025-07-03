package application; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDB {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/pt_pudding"; // 
                String user = "root";
                String password = ""; 

                conn = DriverManager.getConnection(url, user, password);
                System.out.println("tess ");
            } catch (SQLException e) {
                System.out.println("nope: " + e.getMessage());
            }
        }
        return conn;
    }

  
    public static void main(String[] args) {
        getConnection();
    }
}
