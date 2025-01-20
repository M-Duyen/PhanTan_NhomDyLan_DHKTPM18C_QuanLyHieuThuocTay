package database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

import dao.Product_DAO;

public class ConnectDB {
    public static Connection con = null;
    public static ConnectDB instance = new ConnectDB();

    public static ConnectDB getInstance() {
        return instance;
    }

    public void connect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=PharmacyManagement", "sa", "123456789");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Connection getConnection() {
        return con;
    }
    
  //tạo kết nối
    public static Connection getConnectDB_H() {
    	Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=PharmacyManagement";
            con = DriverManager.getConnection(url, "sa", "123456789");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Lỗi 100: Không tìm thấy lớp");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi 101: Không thể kết nối đến máy chủ");
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Lỗi 102: Cấu hình bị trống");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi 103: " + e.getMessage());
        }
        return con;
    }

}
