package dao;

import database.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class Login_DAO {
    private Connection con; // Removed static modifier
    private String username;
    private String password;
    private String ten;

    public Login_DAO() {
        // Initialize con in the constructor
        con = ConnectDB.getConnection();

    }

    /**
     * Lấy tên đăng nhập
     *
     * @param userName
     * @return
     */
    public String containUserName(String userName) {

        try {
            String sql = "SELECT * FROM dbo.Account WHERE accountID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userName);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                username = rs.getString("accountID");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }

    /**
     * Đăng nhập
     *
     * @param userName
     * @param pass
     * @return
     */
    public ArrayList<String> login(String userName, String pass) {
        ArrayList<String> list = new ArrayList<>();
        try {

            PreparedStatement pstmt = null;
            pstmt = con.prepareStatement("SELECT * FROM Account WHERE accountID = ? AND password = ?");
            pstmt.setString(1, userName);
            pstmt.setString(2, pass);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ten = rs.getString("accountID");
                password = rs.getString("password");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        list.add(ten);
        list.add(password);
        return list;

    }

    public static void main(String[] args) {
        ConnectDB.getInstance().connect();
        Login_DAO test = new Login_DAO(); // Create an instance of Test
//        System.out.println(test.dangNhapTen("MyDUYEN"));
        String tenDN = "EP1501";
        String MKDN = "EP1501@";
        ArrayList<String> kq =  test.login(tenDN, MKDN);
        System.out.println(kq);
        System.out.println(test.containUserName(tenDN));

    }
}