package dao;

import database.ConnectDB;
import entity.*;

import java.sql.*;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static java.sql.Date.valueOf;

public class Customer_DAO {
    public Customer_DAO() {

    }

    public static Customer_DAO getInstance() {
        return new Customer_DAO();
    }

    /**
     * Lọc danh sách tất cả khách hàng
     *
     * @return
     */
    public ArrayList<Customer> getListCustomer() {
        Connection con = ConnectDB.getConnectDB_H();
        ArrayList<Customer> cusList = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM Customer");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String cusName = rs.getString(1);
                String phoneNumber = rs.getString(2);
                String cusID = rs.getString(3);
                boolean sex = rs.getBoolean(4);
                LocalDate brithDate = rs.getDate(5).toLocalDate();
                String mail = rs.getString(6);
                String add = rs.getString(8);

                Customer cus = new Customer(cusID, phoneNumber, cusName, mail, sex, add, brithDate);
                cusList.add(cus);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cusList;

    }

    /**
     * Kiểm tra tồn tại của số điện thoại
     *
     * @param phone
     * @return
     */
    public boolean checkPhoneNumber(String phone) {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = con.prepareStatement("SELECT COUNT(*) FROM Customer WHERE phoneNumber = ?");
            statement.setString(1, phone);
            rs = statement.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;// mã nhân viên đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * Thêm khách hàng
     *
     * @param customer
     * @return
     */
    public boolean addCustomer(Customer customer) {
        Connection con = ConnectDB.getConnection();
        String sql = "INSERT INTO Customer(customerID, customerName, phoneNumber, gender, birthDate, email, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int n = 0;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, customer.getCustomerID());
            stmt.setString(2, customer.getCustomerName());
            stmt.setString(3, customer.getPhoneNumber());
            stmt.setBoolean(4, customer.isGender());
            stmt.setDate(5, valueOf(customer.getBrithDate()));
            stmt.setString(6, customer.getEmail());
            stmt.setString(7, customer.getAddr());


            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    /**
     * Tạo mã khách hàng tự động
     *
     * @return
     */
    public String createCustomerID() {
        String newCustomerID = null;
        String prefix = "C";
        String date = new java.text.SimpleDateFormat("ddMMyy").format(new java.util.Date());
        String query = "SELECT MAX(CAST(SUBSTRING(customerID, 8, 3) AS INT)) AS maxCustomerID " +
                "FROM Customer " +
                "WHERE SUBSTRING(customerID, 1, 7) = ?";
        try {
            PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(query);
            preparedStatement.setString(1, prefix + date);
            ResultSet resultSet = preparedStatement.executeQuery();
            int currentMax = 0;
            if (resultSet.next() && resultSet.getObject("maxCustomerID") != null) {
                currentMax = resultSet.getInt("maxCustomerID");
                System.out.println("Mã lớn nhất hiện tại: " + currentMax);
            }
            int nextCustomerID = currentMax + 1;

            newCustomerID = prefix + date + String.format("%03d", nextCustomerID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newCustomerID;
    }

    /**
     * Cập nhật thông tin khách hàng
     *
     * @param customer
     * @return
     */
    public boolean updateCustomer(Customer customer) {
        Connection con = ConnectDB.getConnection();
        String sql = "UPDATE Customer SET customerName = ?, gender = ?, birthDate = ?, email = ?, address = ? WHERE customerID = ? and phoneNumber = ? ";
        int n = 0;
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, customer.getCustomerName());
            stmt.setBoolean(2, customer.isGender());
            stmt.setDate(3, valueOf(customer.getBrithDate()));
            stmt.setString(4, customer.getEmail());
            stmt.setString(5, customer.getAddr());
            stmt.setString(6, customer.getCustomerID());
            stmt.setString(7, customer.getPhoneNumber());

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return n > 0;
    }

    /**
     * Lọc khách hàng theo tiêu chí bất kỳ
     *
     * @param criterous
     * @return
     */
    public ArrayList<Customer> getCustomerByCriterous(String criterous) {
        ArrayList<Customer> customerArrayListByCriterous = new ArrayList<>();
        ArrayList<Customer> customerList = getListCustomer();
        for (Customer customer : customerList) {
            if (customer.getPhoneNumber().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    customer.getCustomerName().toLowerCase().trim().contains(criterous.toLowerCase().trim())
            ) {
                customerArrayListByCriterous.add(customer);
            }
        }
        return customerArrayListByCriterous;
    }

    /**
     * Lọc khách hàng theo số điện thoại
     *
     * @param phone
     * @return
     */
    public Customer getCustomer_ByPhone(String phone) {
        Customer cust = null;
        Connection con = ConnectDB.getConnection();
        PreparedStatement sm = null;
        try {
            sm = con.prepareStatement("SELECT * FROM Customer WHERE phoneNumber = ?");
            sm.setString(1, phone);

            ResultSet rs = sm.executeQuery();

            while (rs.next()) {
                String cusName = rs.getString(1);
                String phoneNumber = rs.getString(2);
                String cusID = rs.getString(3);
                boolean sex = rs.getBoolean(4);
                LocalDate brithDate = rs.getDate(5).toLocalDate();
                String mail = rs.getString(6);
                String add = rs.getString(8);

                cust = new Customer(cusID, phoneNumber, cusName, mail, sex, add, brithDate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cust;
    }

    /**
     * Lấy điểm tích lũy của khách hàng
     *
     * @param phone
     * @return
     */
    public double getCustomerPoint(String phone) {
        Connection con = ConnectDB.getConnection();
        double point = 0;
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("SELECT point FROM Customer WHERE phoneNumber = ?");
            sm.setString(1, phone);

            ResultSet rs = sm.executeQuery();

            if (rs.next()) {
                return point = rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return point;
    }

    /**
     * Giảm điểm tích lũy khi đổi điểm (có transaction)
     *
     * @param phone
     * @param point
     * @param con
     * @return
     */
    public boolean updateCustPoint_Decrease(String phone, double point, Connection con) {
        try {
            PreparedStatement sm = con.prepareStatement("UPDATE Customer SET point = point - ? WHERE phoneNumber = ?");
            sm.setDouble(1, point);
            sm.setString(2, phone);

            return sm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Tăng điểm tích lũy khi mua hàng (có transaction)
     *
     * @param phone
     * @param point
     * @param con
     * @return
     */
    public boolean updateCustPoint_Increase(String phone, double point, Connection con) {
        try {
            PreparedStatement sm = con.prepareStatement("UPDATE Customer SET point = point + ? WHERE phoneNumber = ?");
            sm.setDouble(1, point);
            sm.setString(2, phone);

            return sm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * lưu danh sách customer vào map
     *
     * @return
     */

    public Map<String, Customer> getAllCustomersAsMap() {
        Connection con = ConnectDB.getConnectDB_H();
        Map<String, Customer> customerMap = new HashMap<>();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM Customer");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String cusName = rs.getString(1);
                String phoneNumber = rs.getString(2);
                String cusID = rs.getString(3);
                boolean sex = rs.getBoolean(4);
                LocalDate brithDate = rs.getDate(5).toLocalDate();
                String mail = rs.getString(6);
                String add = rs.getString(8);

                Customer customer = new Customer(cusID, phoneNumber, cusName, mail, sex, add, brithDate);

                customerMap.put(phoneNumber, customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customerMap;
    }

    public static void main(String[] args) throws SQLException {
        ConnectDB.getInstance().connect();
        Customer_DAO cus_dao = new Customer_DAO();

        //cus_dao.getListCustomer().forEach(x -> System.out.println(x));
        //System.out.println(cus_dao.createCustomerID());
//        System.out.println(cus_dao.getCustomerPoint("0123456789"));
//        Customer cus = new Customer("C271024002", "0123456798", "Hồ Quang Minh", "job@yourbusinessname.com", false, "12, Nguyễn Văn Bảo, P.4, Q.GV, HCM", LocalDate.of(2004, 12,12)  );
//        cus_dao.updateCustomer(cus);
        Map<String, Customer> customerMap = cus_dao.getAllCustomersAsMap();
        customerMap.forEach((s, customer) -> {
            System.out.println("ID " + s);
            System.out.println("Name " + customer.getCustomerName());
        });
    }
}
