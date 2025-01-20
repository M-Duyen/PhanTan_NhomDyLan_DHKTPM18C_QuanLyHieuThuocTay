package dao;

import database.ConnectDB;
import entity.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Manager_DAO {
    public static Manager_DAO getInstance(){
        return new Manager_DAO();
    }

    /**
     * Lọc quản lý dựa theo mã quản lý
     *
     * @param managerID
     * @return manager: Manager
     */
    public Manager getManager_ByID(String managerID) {
        Connection con = ConnectDB.getConnectDB_H();
        Manager manager = null;
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT [managerID], [managerName], [birthDate], [phoneNumber] FROM [PharmacyManagement].[dbo].[Manager] WHERE managerID = ?");
            stmt.setString(1, managerID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String managerName = rs.getString("managerName");
                LocalDate birthDate = rs.getDate("birthDate").toLocalDate();
                String phoneNumber = rs.getString("phoneNumber");

                manager = new Manager(managerID, managerName, birthDate, phoneNumber);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return manager;
    }

}