package dao;

import database.ConnectDB;
import entity.AdministrationRoute;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdministrationRoute_DAO {
    public AdministrationRoute_DAO() {
    }

    /**
     * Lọc danh sách tất cả đường dùng
     *
     * @return
     */
    public ArrayList<AdministrationRoute> getAllAdministrationRoute(){
        Connection con = ConnectDB.getConnection();
        ArrayList<AdministrationRoute> list = new ArrayList<>();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM AdministrationRoute");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                String administrationRouteID = rs.getString(1);
                String administrationRouteName = rs.getString(2);

                AdministrationRoute administrationRoute = new AdministrationRoute(administrationRouteID, administrationRouteName);
                list.add(administrationRoute);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Lọc đường dùng theo tiêu chí bất kỳ
     *
     * @param criterous
     * @return
     */
    public ArrayList<AdministrationRoute> getAdminListByCriterous(String criterous) {
        ArrayList<AdministrationRoute> adminListByCri = new ArrayList<>();
        ArrayList<AdministrationRoute> adminList = getAllAdministrationRoute();
        for (AdministrationRoute admin : adminList) {
            if (admin.getAdministrationID().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    admin.getAdministrationName().toLowerCase().trim().contains(criterous.toLowerCase().trim())) {
                adminListByCri.add(admin);
            }
        }
        return adminListByCri;
    }

    public static void main(String[] args) {
        ConnectDB.getInstance().connect();
        AdministrationRoute_DAO ar = new AdministrationRoute_DAO();
        ar.getAllAdministrationRoute().forEach(x -> System.out.println(x));
    }
}
