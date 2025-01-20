package dao;

import database.ConnectDB;
import entity.FunctionalFood;
import entity.MedicalSupplies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MedicalSupplies_DAO {
    public MedicalSupplies_DAO() {
    }

    /**
     * Lọc danh sách tất cả vật tư y tế
     *
     * @param suppliesID
     * @return
     */
    public ArrayList<MedicalSupplies> getMedicalSuppliesListByID(String suppliesID) {
        Connection con = ConnectDB.getInstance().getConnection();
        ArrayList<MedicalSupplies> medicalSuppliesListByID = new ArrayList<>();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SELECT medicalSupplyName FROM MedicalSupplies WHERE medicalSupplyID =? ");
            stmt.setString(1, suppliesID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String medicalSupplyName = rs.getString(1);

                MedicalSupplies medicalSupplies = new MedicalSupplies(medicalSupplyName);
                medicalSuppliesListByID.add(medicalSupplies);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return medicalSuppliesListByID;
    }

    public static void main(String[] args) {
        ConnectDB.getInstance().connect();
        MedicalSupplies_DAO medicalSupplies_dao = new MedicalSupplies_DAO();
        medicalSupplies_dao.getMedicalSuppliesListByID("PS021024000003").forEach(x -> System.out.println(x));
    }
}
