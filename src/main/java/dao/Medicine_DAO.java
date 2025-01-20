package dao;

import database.ConnectDB;
import entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Medicine_DAO {
    public Medicine_DAO() {
    }

    /**
     * Lọc danh sách tất cả thuốc
     *
     * @return
     */
    public ArrayList<Medicine> getMedicineList(){
        Vendor_DAO vendor_dao = new Vendor_DAO();
        Promotion_DAO promotion_dao = new Promotion_DAO();
        Category_DAO category_dao = new Category_DAO();
        Connection con = ConnectDB.getInstance().getConnection();
        ArrayList<Medicine> medicineList = new ArrayList<>();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM Product p join Medicine m ON p.productID = m.medicineID");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){

                String productID = rs.getString(1);
                String productName = rs.getString(2);
                int quanInStock = rs.getInt(3);
                double tax = rs.getDouble(4);
                double purchasePrice = rs.getDouble(5);
                String resNumber = rs.getString(6);

                Promotion promotion = new Promotion();

                if(rs.getString(7) ==  null){
                    promotion = new Promotion();
                }else{
                    ArrayList<Promotion> promotionList = promotion_dao.getPromotionListByCriterous(rs.getString(7));
                    promotion = promotionList.get(0);
                }

                ArrayList<Vendor> vendorList = vendor_dao.getVendorListByCriterias(rs.getString(8));
                Vendor vendor = vendorList.get(0);

                ArrayList<Category> categoryList = category_dao.getCategoryByCriterous(rs.getString(9));
                Category category = categoryList.get(0);

                double sellPrice = rs.getDouble(10);
                String activeIngredient = rs.getString(11);
                String conversionUnit = rs.getString(12);
                AdministrationRoute administrationRoute = new AdministrationRoute(rs.getString(13));

                Medicine medicine = new Medicine(activeIngredient, conversionUnit, administrationRoute);
                medicineList.add(medicine);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medicineList;
    }

    /**
     * Lọc thuốc theo mã
     *
     * @param medicineID
     * @return
     */
    public ArrayList<Medicine> getMedineListByID(String medicineID) {
        AdministrationRoute_DAO administrationRoute_dao = new AdministrationRoute_DAO();
        Connection con = ConnectDB.getInstance().getConnection();
        ArrayList<Medicine> medicineListByID = new ArrayList<>();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SELECT activeIngredient, conversionUnit, administrationID FROM Medicine WHERE medicineID =? ");
            stmt.setString(1, medicineID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String activeIngredient = rs.getString(1);
                String conversionUnit = rs.getString(2);

                ArrayList<AdministrationRoute> adminList = administrationRoute_dao.getAdminListByCriterous(rs.getString(3));
                AdministrationRoute administrationRoute = adminList.get(0);

                Medicine medicine = new Medicine(activeIngredient, conversionUnit, administrationRoute);
                medicineListByID.add(medicine);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return medicineListByID;
    }

    public static void main(String[] args) {
        ConnectDB.getInstance().connect();
        Medicine_DAO medicine_dao = new Medicine_DAO();
        medicine_dao.getMedicineList().forEach(x -> System.out.println(x));
    }
}
