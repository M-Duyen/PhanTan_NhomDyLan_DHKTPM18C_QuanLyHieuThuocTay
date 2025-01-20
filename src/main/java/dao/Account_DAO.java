package dao;

import database.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account_DAO {
    public Account_DAO() {
    }

    /**
     * Lấy email theo accountID
     *
     * @param accountID
     * @return
     */
    public String getEmailByAccountID(String accountID) {
        Connection con = ConnectDB.getConnection();
        String email = "";

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SELECT email FROM Account ac join Employee e ON ac.employeeID = e.employeeID WHERE ac.accountID = ?");
            stmt.setString(1, accountID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                email = rs.getString(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return email;
    }

    /**
     * Cập nhật pass khi quên pass
     *
     * @param accountID
     * @param password
     * @return
     */
    public boolean updatePasswordByAccountID(String accountID, String password) {
        Connection con = ConnectDB.getConnection();
        int n = 0;

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE Account SET password = ? WHERE employeeID = ?");
            stmt.setString(1, password);
            stmt.setString(2, accountID);

            n = stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return n > 0;
    }

    public static void main(String[] args) {
        ConnectDB.getInstance().connect();
        Account_DAO account_dao = new Account_DAO();
//        System.out.println(account_dao.getEmailByAccountID("EP1501"));
        account_dao.updatePasswordByAccountID("EP1501", "EP1501");
    }
}
