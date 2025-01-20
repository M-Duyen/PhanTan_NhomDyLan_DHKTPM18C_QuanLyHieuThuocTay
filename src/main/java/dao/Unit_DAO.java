package dao;

import database.ConnectDB;
import entity.Enum_PackagingUnit;
import entity.Unit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Unit_DAO {
    public static Unit_DAO getInstance(){
        return new Unit_DAO();
    }
    /**
     * Lấy unit theo tên
     *
     * @param unitName
     * @return
     */
    public List<Unit> getUnitByName(String unitName) {
        List<Unit> units = new ArrayList<>();
        String query = "SELECT * FROM Unit WHERE name = ?";
        try (Connection con = ConnectDB.getConnectDB_H();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, unitName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String unitID = rs.getString("unitID");
                String description = rs.getString("describe");
                units.add(new Unit(unitID, unitName, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return units;
    }

    /**
     * Lấy Unit theo mã đơn vị
     *
     * @param unitID
     * @return
     */
    public Unit getUnit_ByID(String unitID) {
        Unit unit = null;
        Connection con = ConnectDB.getConnection();
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("SELECT * FROM Unit WHERE unitID = ?");
            sm.setString(1, unitID);
            ResultSet rs = sm.executeQuery();

            while (rs.next()){
                String unitName = rs.getString("name");
                String description = rs.getString("describe");
                unit = new Unit(unitID, unitName, description);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return unit;
    }

    /**
     * Chuyển đổi Enum unit sang tên unit trong csdl
     *
     * @param unit
     * @return
     */
    public String convertUnit(Enum_PackagingUnit unit) {
        String name = "";
        Connection con = ConnectDB.getConnection();
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("SELECT describe FROM Unit WHERE name = ?");
            sm.setString(1, unit.toString());

            ResultSet rs = sm.executeQuery();
            while (rs.next()){
                name = rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return name;
    }

    /**
     * Tìm unit dựa trên unitEnum
     *
     * @param unitEnum
     * @return
     */
    public Unit getUnit_ByEnumUnit(Enum_PackagingUnit unitEnum) {
        Unit unitEn = null;
        Connection con = ConnectDB.getConnection();
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("SELECT * FROM Unit WHERE name = ?");
            sm.setString(1, unitEnum.toString());

            ResultSet rs = sm.executeQuery();
            while (rs.next()){
                String unitID = rs.getString("unitID");
                String name = rs.getString("name");
                String des = rs.getString("describe");

                unitEn = new Unit(unitID, name, des);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return unitEn;
    }

    /**
     * Tìm theo description trong unit
     *
     * @param des
     * @return
     */
    public String convertDes_ToUnit(String des) {
        String name = "";
        Connection con = ConnectDB.getConnection();
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("SELECT name FROM Unit WHERE describe = ?");
            sm.setString(1, des);

            ResultSet rs = sm.executeQuery();
            while (rs.next()){
                name = rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return name;
    }

    /**
     * Tìm list các đơn vị theo mã sản phẩm
     *
     * @param productID
     * @return
     */
    public ArrayList<Unit> getUnit_ByProductID(String productID){
        ArrayList<Unit> unitList = new ArrayList<>();
        Connection con = ConnectDB.getConnection();
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("SELECT * FROM ProductUnit WHERE productID = ?");
            sm.setString(1, productID);
            ResultSet rs = sm.executeQuery();

            while(rs.next()){
                String unitID = rs.getString("unitID");
                Unit unit = Unit_DAO.getInstance().getUnit_ByID(unitID);

                unitList.add(unit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return unitList;
    }

    /**
     *
     * Tìm Unit theo description
     *
     * @param des
     * @return
     */
    public Unit getUnit_ByDes(String des) {
        Unit unit = null;
        Connection con = ConnectDB.getConnection();
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("SELECT * FROM Unit WHERE describe = ?");
            sm.setString(1, des);

            ResultSet rs = sm.executeQuery();
            while (rs.next()){
                String unitID = rs.getString(1);

                unit = Unit_DAO.getInstance().getUnit_ByID(unitID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return unit;
    }

    public static void main(String[] args) {
        ConnectDB.getInstance().connect();
//        System.out.println(new Unit_DAO().convertUnit(Enum_PackagingUnit.fromString("BLISTER_PACK")));
//        System.out.println(new Unit_DAO().convertDes_ToUnit("Hộp"));
        //Unit_DAO.getInstance().getUnit_ByProductID("PF101224000020").forEach(x -> System.out.println(x));
        System.out.println(Unit_DAO.getInstance().getUnit_ByDes("Hộp"));;
    }
}
