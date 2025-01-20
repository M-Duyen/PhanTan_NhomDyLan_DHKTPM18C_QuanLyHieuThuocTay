package dao;

import database.ConnectDB;
import entity.*;
import ui.model.ModelDataPS;
import ui.model.ModelDataPS_Circle;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.sql.Date.valueOf;

public class OrderDetails_DAO {
    public OrderDetails_DAO() {
    }
    public static OrderDetails_DAO getInstance(){
        return new OrderDetails_DAO();
    }
    /**
     * Lọc tất cả chi tiết hóa đơn theo mã
     *
     * @param orderID
     * @return
     */
    public ArrayList<OrderDetails> getOrderDetailList(String orderID){
        Connection con = ConnectDB.getConnection();
        ArrayList<OrderDetails> list = new ArrayList<>();
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("SELECT * FROM OrderDetail WHERE orderID = ?");
            sm.setString(1, orderID);
            ResultSet rs = sm.executeQuery();

            while(rs.next()){
                Order order = new Order_DAO().getOrder_ByoOrderID(orderID);
                Product product = new Product_DAO().getProduct_ByID(rs.getString("productID"));
                int orderQuantity = rs.getInt("orderQuantity");

                OrderDetails od = new OrderDetails(order, product, orderQuantity);

                list.add(od);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public ArrayList<OrderDetails> getOrderDetailListByScri(String orderID){
        Connection con = ConnectDB.getConnection();
        ArrayList<OrderDetails> list = new ArrayList<>();
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("SELECT * FROM OrderDetail WHERE orderID = ?");
            sm.setString(1, orderID);
            ResultSet rs = sm.executeQuery();

            while(rs.next()){
                ArrayList<Order> listOrder = new Order_DAO().getOrderByCriterious(orderID);
                Order order = listOrder.get(0);
                Product product = new Product_DAO().getProduct_ByID(rs.getString("productID"));
                int orderQuantity = rs.getInt("orderQuantity");

                OrderDetails od = new OrderDetails(order, product, orderQuantity);

                list.add(od);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Thêm chi tiết hóa đơn
     *
     * @param orderDetails
     * @param con
     * @return
     */
    public boolean addOrderDetails(OrderDetails orderDetails, Connection con) {
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO OrderDetail (orderID, productID, unitID, orderQuantity) VALUES (?, ?, ?, ?)");
            stmt.setString(1, orderDetails.getOrder().getOrderID());
            stmt.setString(2, orderDetails.getProduct().getProductID());

            stmt.setString(3, new Unit_DAO().getUnitByName(orderDetails.getUnit().name()).getFirst().getUnitID());
            stmt.setInt(4, orderDetails.getOrderQuantity());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addOrderReturnDetails(String orderID, String productID, int orderQuantity, double gia, String unitID, Connection con) {
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO OrderDetail (orderID, productID, orderQuantity, lineTotal, unitID) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, orderID);
            stmt.setString(2, productID);
            stmt.setInt(3, orderQuantity);
            stmt.setDouble(4, gia);
            stmt.setString(5, unitID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Thống kê sản phẩm bán chạy biểu đồ cột
     *
     * @param start
     * @param end
     * @return
     */
    public ArrayList<ModelDataPS> getProductStatistical(String start, String end){
        ArrayList<ModelDataPS> listPS = new ArrayList<>();

        String startDateWithTime = start + " 00:00:00";
        String endDateWithTime = end + " 23:59:59";
        try {
            String sql = "SELECT TOP 15 p.productID, p.productName, SUM(od.orderQuantity) AS totalSold " +
                    "FROM Orders o  " +
                    "   INNER JOIN OrderDetail od ON o.orderID = od.orderID " +
                    "   INNER JOIN Product p ON od.productID = p.productID " +
                    "   INNER JOIN ProductUnit pu ON pu.productID = p.productID " +
                    "   INNER JOIN Unit u ON u.unitID = pu.unitID " +
                    "WHERE o.orderDate BETWEEN '" + startDateWithTime + "' AND '" + endDateWithTime + "'" +
                    "GROUP BY p.productName, p.productID " +
                    "ORDER BY totalSold DESC";

            String sql1 = "SELECT p.productID, inStock, describe, purchasePrice, sellPrice " +
                    "FROM ProductUnit pu JOIN Unit u ON pu.unitID = u.unitID " +
                    "JOIN Product p ON pu.productID = p.productID " +
                    "WHERE p.productID = ? AND unitNote LIKE name + '%'";

            PreparedStatement p = ConnectDB.getConnectDB_H().prepareStatement(sql);
            PreparedStatement sm = ConnectDB.getConnection().prepareStatement(sql1);
            ResultSet r = p.executeQuery();
            while (r.next()) {
                String productID = r.getString("productID");
                String productName = r.getString("productName");
                int totalSold = r.getInt("totalSold");

                sm.setString(1, productID);
                ResultSet rs = sm.executeQuery();
                int inStock = 0;
                String des = null;
                double purchase = 0;
                double sellPrice = 0;
                if(rs.next()) {
                    inStock = rs.getInt("inStock");
                    des = rs.getString("describe");
                    purchase = rs.getDouble("purchasePrice");
                    sellPrice = rs.getDouble("sellPrice");
                }
                if(des != null) {
                    listPS.add(new ModelDataPS(productName, des, totalSold, inStock, purchase, sellPrice));
                }
            }
            r.close();
            p.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listPS;
    }

    /**
     * Thống kê sản phẩm bán chạy theo ngày, theo loại sản phẩm
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public ArrayList<ModelDataPS_Circle> getProductStatics_ByType(String startDate, String endDate){
        Connection con = ConnectDB.getConnection();
        PreparedStatement sm = null;
        ArrayList<ModelDataPS_Circle> modelList = new ArrayList<>();

        String startDateWithTime = startDate + " 00:00:00";
        String endDateWithTime = endDate + " 23:59:59";
        try {
            sm = con.prepareStatement("SELECT 'Medicine' AS ProductType, COUNT(*) AS Count " +
                    "FROM OrderDetail OD " +
                    "   JOIN Orders O ON OD.orderID = O.orderID " +
                    "   JOIN Medicine M ON OD.productID = M.medicineID " +
                    "WHERE O.orderDate >= ? AND O.orderDate <= ? " +
                    "UNION ALL " +
                    "SELECT 'MedicalSupplies' AS ProductType, COUNT(*) AS Count " +
                    "FROM OrderDetail OD " +
                    "   JOIN Orders O ON OD.orderID = O.orderID " +
                    "   JOIN MedicalSupplies MS ON OD.productID = MS.medicalSupplyID " +
                    "WHERE O.orderDate >= ? AND O.orderDate <= ? " +
                    "UNION ALL " +
                    "SELECT 'FunctionalFood' AS ProductType, COUNT(*) AS Count " +
                    "FROM OrderDetail OD " +
                    "   JOIN Orders O ON OD.orderID = O.orderID " +
                    "   JOIN FunctionalFood F ON OD.productID = F.functionalFoodID " +
                    "WHERE O.orderDate >= ? AND O.orderDate <= ? ");
            sm.setTimestamp(1, Timestamp.valueOf(startDateWithTime));
            sm.setTimestamp(2, Timestamp.valueOf(endDateWithTime));

            sm.setTimestamp(3, Timestamp.valueOf(startDateWithTime));
            sm.setTimestamp(4, Timestamp.valueOf(endDateWithTime));

            sm.setTimestamp(5, Timestamp.valueOf(startDateWithTime));
            sm.setTimestamp(6, Timestamp.valueOf(endDateWithTime));

            ResultSet rs = sm.executeQuery();
            while (rs.next()){
                String name = rs.getString("ProductType");
                int count = rs.getInt("Count");

                ModelDataPS_Circle modelDataPS = new ModelDataPS_Circle(name, count);
                modelList.add(modelDataPS);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modelList;
    }

    /**
     * Thống kê sản phẩm bán chạy theo ngày, theo danh mục (nhóm thuốc)
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public ArrayList<ModelDataPS_Circle> getProductStatics_ByCategory(String startDate, String endDate){
        Connection con = ConnectDB.getConnection();
        PreparedStatement sm = null;
        ArrayList<ModelDataPS_Circle> modelList = new ArrayList<>();

        String startDateWithTime = startDate + " 00:00:00";
        String endDateWithTime = endDate + " 23:59:59";
        try {
            sm = con.prepareStatement("SELECT categoryName, COUNT(*) AS Count " +
                    "FROM OrderDetail OD " +
                    "   JOIN Orders O ON OD.orderID = o.orderID " +
                    "   JOIN Product P ON P.productID = OD.productID " +
                    "   JOIN Category C ON C.categoryID = P.categoryID " +
                    "WHERE O.orderDate >= ? AND O.orderDate <= ? " +
                    "GROUP BY categoryName");
            sm.setTimestamp(1, Timestamp.valueOf(startDateWithTime));
            sm.setTimestamp(2, Timestamp.valueOf(endDateWithTime));

            ResultSet rs = sm.executeQuery();
            while (rs.next()){
                String name = rs.getString("categoryName");
                int count = rs.getInt("Count");

                ModelDataPS_Circle modelDataPS = new ModelDataPS_Circle(name, count);
                modelList.add(modelDataPS);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return modelList;
    }
    /**
     * Lọc giá sản phẩm theo hóa đơn
     *
     * @param orderID
     * @return Danh sách giá sản phẩm theo hóa đơn
     */
    public Map<String, Double> getUnitPricesByOrderID(String orderID) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement sm = null;
        ResultSet rs = null;
        Map<String, Double> unitPrices = new HashMap<>();
        try {
            sm = con.prepareStatement(
                    "SELECT PU.unitID, PU.sellPrice " +
                            "FROM OrderDetail O " +
                            "JOIN ProductUnit PU ON O.productID = PU.productID AND O.unitID = PU.unitID " +
                            "WHERE O.orderID = ?"
            );
            sm.setString(1, orderID);

            rs = sm.executeQuery();
            while (rs.next()) {
                String unitID = rs.getString("unitID");
                double sellPrice = rs.getDouble("sellPrice");

                unitPrices.put(unitID, sellPrice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return unitPrices;
    }


    public Unit createUnit(String unitID) {
        // Khởi tạo kết nối cơ sở dữ liệu
        Connection con = ConnectDB.getConnection();
        String unitName = null;
        String description = null;

        try {
            // Truy vấn từ cơ sở dữ liệu để lấy unitName và description
            String query = "SELECT name, describe FROM Unit WHERE unitID = ?";
            PreparedStatement sm = con.prepareStatement(query);
            sm.setString(1, unitID);
            ResultSet rs = sm.executeQuery();

            // Nếu có kết quả trả về
            if (rs.next()) {
                unitName = rs.getString("name");
                description = rs.getString("describe");
            }

            // Tạo đối tượng Unit và trả về
            if (unitName != null) {
                return new Unit(unitID, unitName, description);
            } else {
                System.out.println("Unit not found.");
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }




    public static void main(String[] args) {
        ConnectDB.getInstance().connect();
        OrderDetails_DAO.getInstance().getProductStatistical("2024-01-01", "2024-12-13").forEach(x -> System.out.println(x));

    }
}
