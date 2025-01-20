package dao;

import database.ConnectDB;
import entity.Prescription;

import java.sql.*;

public class Prescription_DAO {
    public Prescription_DAO() {
    }

    public boolean addPrescription(Prescription pres){
        Connection con = ConnectDB.getConnection();
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("INSERT INTO Prescription VALUES (?, ?, ?, ?)");
            sm.setString(1, pres.getPrescriptionID());
            sm.setDate(2, Date.valueOf(pres.getCreatedDate()));
            sm.setString(3, pres.getDiagnosis());
            sm.setString(4, pres.getMedicalFacility());

            return sm.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
