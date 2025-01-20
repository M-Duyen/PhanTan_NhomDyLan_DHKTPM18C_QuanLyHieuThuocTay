package dao;

import database.ConnectDB;
import entity.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Vendor_DAO {
    public Vendor_DAO() {
    }

    /**
     * Lọc danh sách tất cả nhà cung cấp
     *
     * @return vendorList: ArrayList<Vendor>
     */
    public ArrayList<Vendor> getVendorList() {
        ArrayList<Vendor> vendorList = new ArrayList<>();
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM Vendor");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String vendorID = rs.getString(1);
                String vendorName = rs.getString(2);
                String country = rs.getString(3);

                Vendor vendor = new Vendor(vendorID, vendorName, country);
                vendorList.add(vendor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vendorList;
    }


    /**
     * Lọc nhà cung cấp theo tiêu chí bất kỳ
     *
     * @param criterious
     * @return venDorByCriList: ArrayList<Vendor>
     */
    public ArrayList<Vendor> getVendorListByCriterias(String criterious) {
        ArrayList<Vendor> vendorByCriList = new ArrayList<>();
        ArrayList<Vendor> listVendor = getVendorList();

        for (Vendor vendor : listVendor) {
            if (vendor.getVendorID().toLowerCase().trim().contains(criterious.toLowerCase().trim()) ||
                    vendor.getVendorName().toLowerCase().trim().contains(criterious.toLowerCase().trim()) ||
                    vendor.getCountry().toLowerCase().trim().contains(criterious.toLowerCase().trim())

            ){
                vendorByCriList.add(vendor); // Thêm nhà cung cấp thỏa mãn tiêu chí tìm kiếm vào danh sách kết quả
            }
        }
        return vendorByCriList;
    }

    /**
     * Lọc nhà cung cấp theo quốc gia
     *
     * @param country
     * @return
     */
    public ArrayList<Vendor> getVendorListByCountry(String country) {
        ArrayList<Vendor> vendorByCriList = new ArrayList<>();
        ArrayList<Vendor> listVendor = getVendorList();

        for (Vendor vendor : listVendor) {
            if (vendor.getCountry().toLowerCase().trim().contains(country.trim().toLowerCase())) {
                vendorByCriList.add(vendor);
            }
        }
        return vendorByCriList;
    }

    /**
     * Locj
     *
     * @param criterious
     * @param arrayList
     * @return
     */
    public ArrayList<Vendor> getVendorListByCriteriasByCountry(String criterious, ArrayList<Vendor> arrayList) {
        ArrayList<Vendor> vendorByCriList = new ArrayList<>();

        for (Vendor vendor : arrayList) {
            if (vendor.getVendorID().toLowerCase().trim().contains(criterious.toLowerCase().trim()) ||
                    vendor.getVendorName().toLowerCase().trim().contains(criterious.toLowerCase().trim())
            ){
                vendorByCriList.add(vendor);
            }
        }
        return vendorByCriList;
    }

    /**
     * Lọc nhà cung cấp theo mã
     *
     * @param vendorID
     * @return
     */
    public Vendor getVendor_ByID(String vendorID){
        Vendor vendor = null;
        Connection con = ConnectDB.getConnection();
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("SELECT * FROM Vendor WHERE vendorID = ?");
            sm.setString(1, vendorID);
            ResultSet rs = sm.executeQuery();

            while (rs.next()){
                vendor = new Vendor(vendorID, rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vendor;
    }

    /**
     * Kiểm tra tồn tại của nhà cung cấp
     *
     * @param vendorID
     * @return vd : ArrayList<Vendor>
     */
    public ArrayList<Vendor> checkVendorExistence(String vendorID) {
        ArrayList<Vendor> vd = getVendorListByCriterias(vendorID);
        return vd;
    }

    /**
     * Thêm nhà cung cấp
     *
     * @param vendor
     * @return
     */
    public boolean addVendor(Vendor vendor) {
        Connection con = ConnectDB.getInstance().getConnection();
        String sql = "INSERT INTO Vendor(vendorID, vendorName, country) VALUES (?, ?, ?)";
        int n = 0;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, vendor.getVendorID());
            stmt.setString(2, vendor.getVendorName());
            stmt.setString(3, vendor.getCountry());

            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

//    public boolean updateVendor(Vendor vendor) {
//        Connection con = ConnectDB.getInstance().getConnection();
//        int n = 0;
//        try {
//            PreparedStatement stmt = con.prepareStatement("UPDATE Vendor SET vendorName = ?, country = ? WHERE vendorID = ?");
//            stmt.setString(1, vendor.getVendorName());
//            stmt.setString(2, vendor.getCountry());
//            stmt.setString(3, vendor.getVendorID());
//
//            n = stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return n > 0;
//    }

    //Tạo mã hoá đơn
//    public String taoMaHD() throws SQLException {
//        String newMaHoaDon = null;
//        LocalDate today = LocalDate.now();
//        String currentDate = today.format(DateTimeFormatter.ofPattern("ddMMyy"));
//        String query = "SELECT MAX(CAST(SUBSTRING(maHoaDon, 9, 3) AS INT)) AS maxMaHoaDon " +
//                "FROM HoaDon " +
//                "WHERE SUBSTRING(maHoaDon, 3, 6) = ?";
//
//        try (PreparedStatement preparedStatement = connectDB.getConnectDB().prepareStatement(query)) {
//            preparedStatement.setString(1, currentDate);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            int currentMax = 0;
//            if (resultSet.next() && resultSet.getObject("maxMaHoaDon") != null) {
//                currentMax = resultSet.getInt("maxMaHoaDon");
//                System.out.println(currentMax + "\n");
//            }
//            int nextMaHoaDon = currentMax + 1;
//            newMaHoaDon = "HD" + currentDate + String.format("%03d", nextMaHoaDon);
//        }
//
//        return newMaHoaDon;
//    }


    /**
     * Tạo mã tự động cho nhà cung cấp
     *
     * @param country
     * @return
     * @throws SQLException
     */
    public String createVendorID(String country) throws SQLException {
        String newMaNCC = null;

        // Lấy mã quốc gia (2 ký tự đầu của tên quốc gia, in hoa)
        String countryCode = getCountryID(country);

        // Chuỗi tiền tố mã nhà cung cấp
        String prefix = "VD" + countryCode;

        // Truy vấn lấy số thứ tự lớn nhất cho mã nhà cung cấp có cùng tiền tố
        String query = "SELECT MAX(CAST(SUBSTRING(vendorID, 5, 3) AS INT)) AS maxMaNCC " +
                "FROM Vendor " +
                "WHERE SUBSTRING(vendorID, 1, 4) = ?";

        try (PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, prefix);

            ResultSet resultSet = preparedStatement.executeQuery();
            int currentMax = 0;
            if (resultSet.next() && resultSet.getObject("maxMaNCC") != null) {
                currentMax = resultSet.getInt("maxMaNCC");
                System.out.println("Mã lớn nhất hiện tại: " + currentMax);
            }

            // Tăng số thứ tự lên 1
            int nextMaNCC = currentMax + 1;

            // Tạo mã nhà cung cấp mới
            newMaNCC = prefix + String.format("%03d", nextMaNCC);
        }
        return newMaNCC;
    }

    /**
     *  Lấy mã quốc gia theo quốc gia
     *
     * @param country
     * @return
     */
    private String getCountryID(String country) {
        String countryNot = removeAccent(country);
        String[] words = countryNot.split(" ");
        StringBuilder countryCode = new StringBuilder();

        if (words.length > 0) {
            if (words.length == 1) {
                // Nếu tên quốc gia chỉ có một từ, lấy 2 ký tự đầu của từ đó
                countryCode.append(words[0].substring(0, Math.min(2, words[0].length())));
            } else {
                // Lấy ký tự đầu của từ đầu tiên
                countryCode.append(words[0].charAt(0));
                // Lấy ký tự đầu của từ thứ hai
                countryCode.append(words[1].charAt(0));
            }
        }
        // Chuyển mã quốc gia thành chữ in hoa
        return countryCode.toString().toUpperCase();
    }

    /**
     * Chuyển đầu vào thành ký tự không dấu
     *
     * @param input
     * @return
     */
    public static String removeAccent(String input) {
        // Chuyển đổi sang dạng chuẩn
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Sử dụng biểu thức chính quy để loại bỏ các ký tự dấu
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").replaceAll("đ", "d").replaceAll("Đ", "D");
    }

    public static void main(String[] args) throws SQLException {
        ConnectDB.getInstance().connect();
        Vendor_DAO vd_dao = new Vendor_DAO();
        //vd_dao.getVendorList().forEach(x -> System.out.println(x));
        System.out.println(vd_dao.getVendor_ByID("VDVN001"));
///        System.out.println(vd_dao.getVendorListByCriteroais("việt nam"));
//        Vendor vd = new Vendor("VDVN004", "Công ty CP Dược Phẩm Nano", "Vệt Nam");
//        vd_dao.updateVendor(vd);
//        vd_dao.getVendorListByCountry("Việt Nam");
//        System.out.println(vd_dao.createVendorID("Việt Nam"));

//        System.out.println(vd_dao.getCountryID("Nam Định"));
    }
}

