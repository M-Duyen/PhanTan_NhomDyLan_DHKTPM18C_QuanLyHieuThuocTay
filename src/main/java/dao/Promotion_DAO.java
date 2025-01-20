package dao;

import database.ConnectDB;
import entity.Promotion;
import entity.PromotionType;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Promotion_DAO {
    public Promotion_DAO() {
    }

    /**
     * Lọc danh sách tất cả khuyến mãi
     *
     * @return
     */
    public ArrayList<Promotion> getPromotionList() {
        PromotionType_DAO promotionType_dao = new PromotionType_DAO();
        Connection con = ConnectDB.getConnection();
        ArrayList<Promotion> promotionList = new ArrayList<>();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM Promotion");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String promotionID = rs.getString("promotionID");
                String promotionName = rs.getString("promotionName");
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                double discount = rs.getDouble("disCountPercent");
                boolean stats = rs.getBoolean("status");
                String promotionTypeID = rs.getString("promotionTypeID");

                PromotionType promotionType = null;

                if (promotionTypeID != null) {
                    ArrayList<PromotionType> promotionTypeList = promotionType_dao.getPromotionTypeByCriterous(promotionTypeID);
                    promotionType = !promotionTypeList.isEmpty() ? promotionTypeList.get(0) : null;
                }

                Promotion promotion = new Promotion(promotionID, promotionName, startDate, endDate, discount, stats, promotionType);
                promotionList.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotionList;
    }

    /**
     * Lọc khuyến mãi theo tiêu chí bất kỳ
     *
     * @param criterious
     * @return
     */
    public ArrayList<Promotion> getPromotionListByCriterous(String criterious) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = null;

        if (criterious != null && !criterious.trim().isEmpty()) {
            try {
                date = LocalDate.parse(criterious.trim(), dateFormatter);
            } catch (DateTimeParseException e) {

            }
        }

        ArrayList<Promotion> promotionListByCriterous = new ArrayList<>();
        ArrayList<Promotion> promotionList = getPromotionList();
        for (Promotion promotion : promotionList) {
            if (promotion.getPromotionID().toLowerCase().trim().contains(criterious.toLowerCase().trim()) ||
                    promotion.getPromotionName().toLowerCase().trim().contains(criterious.toLowerCase().trim()) ||
                    (date != null && promotion.getStartDate().equals(date)) ||
                    (date != null && promotion.getEndDate().equals(date)) ||
                    promotion.getPromotionType().getPromotionTypeID().toLowerCase().trim().contains(criterious.toLowerCase().trim()) ||
                    promotion.getPromotionType().getPromotionTypeName().toLowerCase().trim().contains(criterious.toLowerCase().trim())

            ) {
                promotionListByCriterous.add(promotion);
            }
        }

        return promotionListByCriterous;
    }

    /**
     * Lọc danh sách promotion theo status
     *
     * @param status
     * @return
     */
    public ArrayList<Promotion> getPromotionListByStatus(boolean status) {
        Connection con = ConnectDB.getConnection();
        PromotionType_DAO promotionType_dao = new PromotionType_DAO();
        ArrayList<Promotion> promotionList = new ArrayList<>();
        String sql = "SELECT * FROM Promotion WHERE stats = ?";

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(sql);
            stmt.setBoolean(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String promotionID = rs.getString(1);
                String promotionName = rs.getString(2);
                LocalDate startDate = rs.getDate(3).toLocalDate();
                LocalDate endDate = rs.getDate(4).toLocalDate();
                String promotionTypeID = rs.getString(5);

                PromotionType promotionType = null;

                if (promotionTypeID != null) {
                    ArrayList<PromotionType> promotionTypeList = promotionType_dao.getPromotionTypeByCriterous(promotionTypeID);
                    promotionType = !promotionTypeList.isEmpty() ? promotionTypeList.get(0) : null;
                }

                boolean stats = rs.getBoolean(6);
                double discount = rs.getDouble(7);

                Promotion promotion = new Promotion(promotionID, promotionName, startDate, endDate, discount, stats, promotionType);
                promotionList.add(promotion);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return promotionList;
    }


    /**
     * Lọc khuyến mãi theo tiêu chí bất kỳ
     *
     * @param criterious
     * @return
     */
    public ArrayList<Promotion> getPromotionListByCriterous(boolean criterious, ArrayList<Promotion> proList) {

        ArrayList<Promotion> promotionListByCriterous = new ArrayList<>();
        ArrayList<Promotion> promotionList = proList;
        for (Promotion promotion : promotionList) {
            if (promotion.isStats() == criterious) {
                promotionListByCriterous.add(promotion);
            }
        }

        return promotionListByCriterous;
    }

    /**
     * Thêm khuyến mãi
     *
     * @param promotion
     * @return
     */
    public boolean addPromotion(Promotion promotion) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n = 0;

        String sql = "INSERT INTO Promotion (promotionID, promotionName, startDate, endDate, promotionTypeID, status, disCountPercent) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, promotion.getPromotionID());
            stmt.setString(2, promotion.getPromotionName());
            stmt.setDate(3, Date.valueOf(promotion.getStartDate()));
            stmt.setDate(4, Date.valueOf(promotion.getEndDate()));
            stmt.setString(5, promotion.getPromotionType().getPromotionTypeID());
            stmt.setBoolean(6, promotion.isStats());
            stmt.setDouble(7, promotion.getDiscount());

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return n > 0;
    }

    /**
     * Tạo mã khuyến mãi tự động
     *
     * @param startDate
     * @param endDate
     * @return
     */

    public String createPromotionID(String startDate, String endDate) {
        String newPromotionID = null;
        String prefix = "PR";

        String query = "SELECT MAX(CAST(SUBSTRING(promotionID, 15, 2) AS INT)) AS maxPromotionID " +
                "FROM Promotion " +
                "WHERE SUBSTRING(promotionID, 1, 14) = ?";
        try {
            PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(query);
            preparedStatement.setString(1, prefix + startDate + endDate);
            ResultSet resultSet = preparedStatement.executeQuery();


            int currentMax = 0;
            if (resultSet.next() && resultSet.getObject("maxPromotionID") != null) {
                currentMax = resultSet.getInt("maxPromotionID");
            }

            // Tăng mã số cuối lên 1 để tạo mã Promotion mới
            int nextPromotionID = currentMax + 1;
            newPromotionID = prefix + startDate + endDate + String.format("%02d", nextPromotionID);

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tạo Promotion ID: " + e.getMessage(), e);
        }
        return newPromotionID;
    }

    /**
     * Cập nhật trạng thái của khuyến mãi
     */

    public void updatePromotionStatus() {
        Connection con = ConnectDB.getConnection();
        String sql = "{call UpdatePromotionStatus}";

        try {
            CallableStatement stmt = con.prepareCall(sql);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) {
        ConnectDB.getInstance().connect();
        Promotion_DAO promotion_dao = new Promotion_DAO();
//        promotion_dao.getPromotionList().forEach(x -> System.out.println(x));
        promotion_dao.getPromotionListByStatus(false).forEach(System.out::println);
//        System.out.println( promotion_dao.createPromotionID("081124", "081124"));

//        promotion_dao.getPromotionList().forEach(System.out::println);

    }

}
