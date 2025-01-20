package dao;

import database.ConnectDB;
import entity.*;
import ui.model.ModelDataRS;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Order_DAO {
    public Order_DAO() {
    }

    public static Order_DAO getInstance() {
        return new Order_DAO();
    }

    /**
     * Lọc danh sách tất cả hóa đơn
     *
     * @return
     */
    public ArrayList<Order> getOrderList() {
        Connection con = ConnectDB.getConnectDB_H();
        ArrayList<Order> orderList = new ArrayList<>();

        try (
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM Orders");
                ResultSet rs = stmt.executeQuery()) {

            // Tải trước danh sách nhân viên và khách hàng
            Map<String, Customer> customerCache = new Customer_DAO().getAllCustomersAsMap();
            Map<String, Employee> employeeCache = new Employee_DAO().getAllEmployeesAsMap();

            while (rs.next()) {
                // Lấy dữ liệu cơ bản của đơn hàng
                String orderID = rs.getString("orderID");
                LocalDateTime orderDate = rs.getTimestamp("orderDate").toLocalDateTime();
                String shipToAddress = rs.getString("shipToAddress");
                String paymentMethod = rs.getString("paymentMethod");
                double discount = rs.getDouble("discount");

                // Lấy Customer
                String customerID = rs.getString("customerID");
                Customer customer = customerCache.getOrDefault(customerID, new Customer());

                // Lấy Employee
                String employeeID = rs.getString("employeeID");
                Employee employee = employeeCache.getOrDefault(employeeID, new Employee());

                // Lấy Prescription
                String prescriptionID = rs.getString("prescriptionID");
                Prescription prescription = prescriptionID != null ? new Prescription(prescriptionID) : null;

                // Tạo Order
                Order order = new Order(
                        orderID,
                        orderDate,
                        shipToAddress,
                        Enum_PaymentMethod.valueOf(paymentMethod),
                        discount,
                        employee,
                        customer,
                        prescription
                );

                // Thêm vào danh sách
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách đơn hàng: ", e);
        }

        return orderList;
    }



    /**
     * Lọc hóa đơn theo mã hóa đơn
     *
     * @param orderID
     * @return
     */
    public Order getOrder_ByoOrderID(String orderID) {
        Connection con = ConnectDB.getConnectDB_H();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM Orders WHERE orderID = ?");
            stmt.setString(1, orderID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                LocalDateTime orderDate = LocalDateTime.from(rs.getTimestamp("orderDate").toLocalDateTime());
                String shipToAddress = rs.getString("shipToAddress");
                String paymentMethod = rs.getString("paymentMethod");
                double discount = rs.getDouble("discount");
                Customer customer = Customer_DAO.getInstance().getCustomer_ByPhone(rs.getString("customerID"));
                Employee emp = Employee_DAO.getInstance().getEmployee_ByID(rs.getString("employeeID"));
                Prescription prescription = new Prescription(rs.getString("prescriptionID"));

                Order order = new Order(orderID, orderDate, shipToAddress, Enum_PaymentMethod.valueOf(paymentMethod), discount, emp, customer, prescription);

                return order;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Tạo mã tự động cho hóa đơn
     *
     * @return
     */
    public String createOrderID(String emplID) {
        String newID = null;
        PreparedStatement sm = null;
        int currentMax = 0;
        Connection con = ConnectDB.getConnection();
        String datePart = new SimpleDateFormat("ddMMyy").format(new java.util.Date());
        String query = "SELECT MAX(CAST(SUBSTRING(orderID, 13, 3) AS INT)) AS maxID " +
                "FROM Orders " +
                "WHERE SUBSTRING(orderID, 3, 6) = ?";

        try {
            sm = con.prepareStatement(query);
            sm.setString(1, datePart);
            ResultSet resultSet = sm.executeQuery();

            if (resultSet.next() && resultSet.getObject("maxID") != null) {
                currentMax = resultSet.getInt("maxID");
            }
            int nextID = currentMax + 1;
            newID = "OR" + datePart + emplID.substring(2) + String.format("%03d", nextID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newID;
    }

    /**
     * Tìm kiếm hóa đơn theo tiêu chí bất kì
     * định dạng ngày khi nhập (yyyy-MM-dd)
     *
     * @param criterious
     * @return
     */
    public ArrayList<Order> getOrderByCriterious(String criterious) {

        String lowerCriterious = (criterious != null) ? criterious.toLowerCase() : "";
        // Bây giờ bạn có lowerCriterious với giá trị mặc định nếu criterious là null

        if (lowerCriterious != null) {
            ArrayList<Order> orderListByCri = new ArrayList<>();
            ArrayList<Order> orderList = getOrderList();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime orderDate = null;

            if (lowerCriterious != null && !lowerCriterious.trim().isEmpty()) {
                try {
                    orderDate = LocalDateTime.parse(lowerCriterious.trim(), dateFormatter);
                } catch (DateTimeParseException e) {

                }
            }
            for (Order o : orderList) {
                boolean matches =
                        (o.getOrderID() != null && o.getOrderID().toLowerCase().contains(lowerCriterious.toLowerCase())) ||
                                (o.getOrderDate().equals(orderDate)) ||
                                (o.getShipToAddress() != null && o.getShipToAddress().toLowerCase().contains(lowerCriterious)) ||
                                (o.getPaymentMethod() != null && o.getPaymentMethod().getPaymentMethod().equals(lowerCriterious.toUpperCase())) ||
                                (o.getEmployee() != null && o.getEmployee().getEmployeeID() != null && o.getEmployee().getEmployeeID().toLowerCase().contains(lowerCriterious.toLowerCase())) ||
                                (o.getEmployee() != null && o.getEmployee().getEmployeeName() != null && o.getEmployee().getEmployeeName().toLowerCase().contains(lowerCriterious.toLowerCase())) ||
                                (o.getCustomer() != null && o.getCustomer().getPhoneNumber() != null && o.getCustomer().getPhoneNumber().contains(lowerCriterious.trim())) ||
                                (o.getPrescription() != null && o.getPrescription().getPrescriptionID() != null && o.getPrescription().getPrescriptionID().toLowerCase().contains(lowerCriterious.trim().toLowerCase()));

                if (matches) {
                    orderListByCri.add(o);
                }
            }
            return orderListByCri;
        } else {
            throw new IllegalArgumentException("Null");
        }
    }

    /**
     * Tính doanh thu theo 1 tiêu chí bất kì
     *
     * @param criteria
     * @return
     */
    public double getRevenueByCriteria(String criteria) {
        double totalRevenue = 0.0;
        ArrayList<Order> orderList = getOrderList();

        for (Order order : orderList) {
            boolean matchesCriteria = false;
            // Kiểm tra tiêu chí có thể là orderID
            if (order.getOrderID().toLowerCase().trim().contains(criteria.toLowerCase().trim())) {
                matchesCriteria = true;
            }
            // Kiểm tra tiêu chí có thể là ngày đặt hàng
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                LocalDate criteriaDate = LocalDate.parse(criteria, dateFormatter);
                if (order.getOrderDate().equals(criteriaDate)) {
                    matchesCriteria = true;
                }
            } catch (DateTimeParseException e) {
            }
            // Kiểm tra tiêu chí có thể là phương thức thanh toán
            if (order.getPaymentMethod().getPaymentMethod().equals(criteria.toUpperCase().trim())) {
                matchesCriteria = true;
            }
            // Kiểm tra tiêu chí có thể là employeeID
            if (order.getEmployee().getEmployeeID().toLowerCase().trim().contains(criteria.toLowerCase().trim())) {
                matchesCriteria = true;
            }
            // Kiểm tra tiêu chí có thể là customer phone number
            if (order.getCustomer().getPhoneNumber().contains(criteria.trim())) {
                matchesCriteria = true;
            }
            // Nếu đơn hàng khớp với tiêu chí, cộng doanh thu
            if (matchesCriteria) {
                totalRevenue += order.getTotalDue();
            }
        }
        return totalRevenue;
    }

    /**
     * Tính doanh thu trong 1 khoảng thời gian bất kì
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public double getRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        double totalRevenue = 0.0;
        ArrayList<Order> orderList = getOrderList();
        for (Order order : orderList) {
            if ((order.getOrderDate().isEqual(startDate) || order.getOrderDate().isAfter(startDate)) &&
                    (order.getOrderDate().isEqual(endDate) || order.getOrderDate().isBefore(endDate))) {
                totalRevenue += order.getTotalDue();
            }
        }
        return totalRevenue;
    }

    public ArrayList<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        Connection con = ConnectDB.getConnection();
        ;
        PreparedStatement stmt = null;
        ArrayList<Order> orderListByCri = new ArrayList<>();
        try {

            stmt = con.prepareStatement("SELECT * FROM Orders WHERE orderDate BETWEEN ? AND ?");
            stmt.setTimestamp(1, Timestamp.valueOf(startDate));
            stmt.setTimestamp(2, Timestamp.valueOf(endDate));

            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                String orderID = rs.getString(1);
                LocalDateTime orderDate = rs.getTimestamp(2).toLocalDateTime();
                String shipToAddress = rs.getString(3);
                String paymentMethod = rs.getString(4);
                double discount = rs.getDouble(5);

                Customer_DAO customer_dao = new Customer_DAO();
                Customer customer;
                ArrayList<Customer> cusList = customer_dao.getCustomerByCriterous(rs.getString(7));
                if (cusList.isEmpty()) {
                    customer = new Customer();
                } else {
                    customer = cusList.get(0);
                }

                Employee_DAO employee_dao = new Employee_DAO();
                Employee employee;

                ArrayList<Employee> empList = employee_dao.getEmployeeByCriterious(rs.getString(8));
                employee = empList.get(0);


                Prescription prescription = new Prescription(rs.getString(9));

                Order order = new Order(orderID, orderDate, shipToAddress, Enum_PaymentMethod.valueOf(paymentMethod), discount, employee, customer, prescription);
                orderListByCri.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderListByCri;
    }


    /**
     * Lọc danh sách hóa đơn theo ngày hiện tại của nhân viên
     *
     * @param empID
     * @return
     */
    public ArrayList<Order> filterOrderByEmpID(String empID) {
        Connection con = ConnectDB.getConnection();
        ArrayList<Order> orderList = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT *  FROM Orders o WHERE o.employeeID = ? \n" +
                    "  AND CAST(o.orderDate AS DATE) = CAST(GETDATE() AS DATE)");
            stmt.setString(1, empID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String orderID = rs.getString("orderID");
                LocalDateTime orderDate = rs.getTimestamp("orderDate").toLocalDateTime();
                String shipToAddress = rs.getString("shipToAddress");
                String paymentMethod = rs.getString("paymentMethod");
                double discount = rs.getDouble("discount");

                Customer customer;

                if (rs.getString("customerID") != null) {
                    customer = new Customer_DAO().getCustomer_ByPhone(rs.getString("customerID"));
                } else {
                    customer = new Customer();
                }

                Employee emp;

                if (rs.getString("employeeID") != null) {
                    emp = new Employee_DAO().getEmployee_ByID(rs.getString("employeeID"));
                } else {
                    emp = new Employee();
                }

                Prescription prescription = new Prescription(rs.getString("prescriptionID"));

                Order order = new Order(orderID, orderDate, shipToAddress, Enum_PaymentMethod.valueOf(paymentMethod), discount, emp, customer, prescription);
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }

    /**
     * Tính doanh thu theo ngày hiện tại của nhân viên
     *
     * @param empID
     * @return
     */
    public double calculateTotalAllOrder(String empID) {
        double total = 0;
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT employeeID, SUM(totalDue) AS totalDueSum\n" +
                    "FROM Orders \n" +
                    "WHERE employeeID = ? AND CAST(orderDate AS DATE) = CAST(GETDATE() AS DATE)\n" +
                    "GROUP BY employeeID");
            stmt.setString(1, empID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                total = rs.getDouble(2);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return total;
    }

    /**
     * Thêm hóa đơn
     *
     * @param order
     * @return
     */
    public boolean addOrder(Order order, Connection con) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO Orders (orderID, orderDate, shipToAddress, paymentMethod, discount, customerID, employeeID, prescriptionID) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, order.getOrderID());
            stmt.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
            stmt.setString(3, order.getShipToAddress());
            stmt.setString(4, order.getPaymentMethod().name());
            stmt.setDouble(5, order.getDiscount());
            if (order.getCustomer() != null) {
                stmt.setString(6, order.getCustomer().getPhoneNumber());
            } else {
                stmt.setNull(6, Types.VARCHAR);
            }
            stmt.setString(7, order.getEmployee().getEmployeeID());
            if (order.getPrescription() != null) {
                stmt.setString(8, order.getPrescription().getPrescriptionID()); // Thêm prescriptionID
            } else {
                stmt.setNull(8, Types.VARCHAR);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật tổng tiền nếu có khuyến mãi
     *
     * @param order
     * @param con
     * @return
     */
    public boolean updateTotalDueWithDiscount(Order order, Connection con) {
        try {
            PreparedStatement sm = con.prepareStatement("UPDATE Orders SET totalDue = totalDue - ? WHERE orderID = ?");
            sm.setDouble(1, order.getDiscount());
            sm.setString(2, order.getOrderID());

            return sm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Lấy totalDue của hóa đơn
     *
     * @param orderID
     * @return
     */
    public double getTotalDue(String orderID) {
        Connection con = ConnectDB.getConnectDB_H();
        PreparedStatement sm = null;
        double totalDue = 0;
        try {
            sm = con.prepareStatement("SELECT totalDue FROM Orders WHERE orderID = ?");
            sm.setString(1, orderID);

            ResultSet rs = sm.executeQuery();
            if (rs.next()) {
                totalDue = rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return totalDue;
    }
    /**
     * Thống kê theo năm (trục tung là doanh thu, trục hoành là tháng)
     *
     * @param year
     * @return
     */
    public ArrayList<ModelDataRS> getModelDataRSByYear(int year) {
        ArrayList<ModelDataRS> modelList = new ArrayList<>();
        try {
            String sql = "SELECT " +
                    "CASE FORMAT(ODate, 'MMMM') " +
                    "WHEN 'January' THEN 'Tháng 1' " +
                    "WHEN 'February' THEN 'Tháng 2' " +
                    "WHEN 'March' THEN 'Tháng 3' " +
                    "WHEN 'April' THEN 'Tháng 4' " +
                    "WHEN 'May' THEN 'Tháng 5' " +
                    "WHEN 'June' THEN 'Tháng 6' " +
                    "WHEN 'July' THEN 'Tháng 7' " +
                    "WHEN 'August' THEN 'Tháng 8' " +
                    "WHEN 'September' THEN 'Tháng 9' " +
                    "WHEN 'October' THEN 'Tháng 10' " +
                    "WHEN 'November' THEN 'Tháng 11' " +
                    "WHEN 'December' THEN 'Tháng 12' " +
                    "END AS [Month], " +
                    "SUM(totalDue) AS TotalAmount, " +
                    "SUM(TongM) AS M, " +
                    "SUM(TongS) AS S, " +
                    "SUM(TongF) AS F " +
                    "FROM ( " +
                    "SELECT " +
                    "O.orderID, " +
                    "O.orderDate AS ODate, " +
                    "SUM(OD.lineTotal) AS totalDue, " +
                    "SUM(CASE WHEN SUBSTRING(OD.productID, 2, 1) = 'M' THEN OD.lineTotal ELSE 0 END) AS TongM, " +
                    "SUM(CASE WHEN SUBSTRING(OD.productID, 2, 1) = 'S' THEN OD.lineTotal ELSE 0 END) AS TongS, " +
                    "SUM(CASE WHEN SUBSTRING(OD.productID, 2, 1) = 'F' THEN OD.lineTotal ELSE 0 END) AS TongF " +
                    "FROM " +
                    "Orders AS O " +
                    "INNER JOIN " +
                    "OrderDetail AS OD ON O.orderID = OD.orderID " +
                    "WHERE YEAR(O.orderDate) = " + year +
                    "GROUP BY " +
                    "O.orderID, O.orderDate " +
                    ") AS SubQuery " +
                    "GROUP BY " +
                    "FORMAT(ODate, 'MMMM') " +
                    "ORDER BY " +
                    "MIN(ODate) DESC ";
            PreparedStatement p = ConnectDB.getConnectDB_H().prepareStatement(sql);
            ResultSet r = p.executeQuery();
            while (r.next()) {
                String month = r.getString("Month");
                double all = r.getDouble("TotalAmount");
                double revenueMedicine = r.getDouble("M");
                double revenueMedicalS = r.getDouble("S");;
                double revenueFunctionalFood = r.getDouble("F");;
                modelList.add(new ModelDataRS(month, all, revenueMedicine, revenueMedicalS, revenueFunctionalFood));
            }
            r.close();
            p.close();
        } catch (Exception e) {
        }
        return modelList;
    }

    /**
     * Thống kê theo tháng, trục tung là doanh thu, trục hoành là các ngày trong tháng đó
     *
     * @param month
     * @param year
     * @return
     */
    public ArrayList<ModelDataRS> getModelDataRSByYearByMonth(int month, int year){
        ArrayList<ModelDataRS> modelList = new ArrayList<>();
        try {
            String sql = "SELECT " +
                    "N'Ngày ' + CAST(DAY(ODate) AS NVARCHAR(2)) AS [Day]," +
                    "SUM(totalDue) AS TotalAmount, " +
                    "SUM(TongM) AS M, " +
                    "SUM(TongS) AS S, " +
                    "SUM(TongF) AS F " +
                    "FROM ( " +
                    "SELECT " +
                    "O.orderID, " +
                    "O.orderDate AS ODate, " +
                    "SUM(OD.lineTotal) AS totalDue, " +
                    "SUM(CASE WHEN SUBSTRING(OD.productID, 2, 1) = 'M' THEN OD.lineTotal ELSE 0 END) AS TongM, " +
                    "SUM(CASE WHEN SUBSTRING(OD.productID, 2, 1) = 'S' THEN OD.lineTotal ELSE 0 END) AS TongS, " +
                    "SUM(CASE WHEN SUBSTRING(OD.productID, 2, 1) = 'F' THEN OD.lineTotal ELSE 0 END) AS TongF " +
                    "FROM " +
                    "Orders AS O " +
                    "INNER JOIN " +
                    "OrderDetail AS OD ON O.orderID = OD.orderID " +
                    "WHERE YEAR(O.orderDate) = " + year + " AND MONTH(O.orderDate) = " + month  +
                    "GROUP BY " +
                    "O.orderID, O.orderDate " +
                    ") AS SubQuery " +
                    "GROUP BY " +
                    "DAY(ODate) " +
                    "ORDER BY " +
                    "DAY(ODate) DESC ";
            PreparedStatement p = ConnectDB.getConnectDB_H().prepareStatement(sql);
            ResultSet r = p.executeQuery();
            while (r.next()) {
                String day = r.getString("Day");
                double all = r.getDouble("TotalAmount");
                double revenueMedicine = r.getDouble("M");
                double revenueMedicalS = r.getDouble("S");;
                double revenueFunctionalFood = r.getDouble("F");;
                modelList.add(new ModelDataRS(day, all, revenueMedicine, revenueMedicalS, revenueFunctionalFood));
            }
            r.close();
            p.close();
        } catch (Exception e) {
        }
        return modelList;
    }

    /**
     * Thống kê theo tùy chỉnh, chỉ thống kê trong thời gian tối đa 30 ngày
     *
     * @param start
     * @param end
     * @return
     */
    public ArrayList<ModelDataRS> getModelDataRSByYearByTime(String start, String end){
        ArrayList<ModelDataRS> modelList = new ArrayList<>();
        try {
            String sql = "SELECT " +
                    "FORMAT(ODate, 'dd/MM/yy') AS [Day], " +
                    "SUM(totalDue) AS TotalAmount, " +
                    "SUM(TongM) AS M, " +
                    "SUM(TongS) AS S, " +
                    "SUM(TongF) AS F " +
                    "FROM ( " +
                    "SELECT " +
                    "O.orderID, " +
                    "O.orderDate AS ODate, " +
                    "SUM(OD.lineTotal) AS totalDue, " +
                    "SUM(CASE WHEN SUBSTRING(OD.productID, 2, 1) = 'M' THEN OD.lineTotal ELSE 0 END) AS TongM, " +
                    "SUM(CASE WHEN SUBSTRING(OD.productID, 2, 1) = 'S' THEN OD.lineTotal ELSE 0 END) AS TongS, " +
                    "SUM(CASE WHEN SUBSTRING(OD.productID, 2, 1) = 'F' THEN OD.lineTotal ELSE 0 END) AS TongF " +
                    "FROM " +
                    "Orders AS O " +
                    "INNER JOIN " +
                    "OrderDetail AS OD ON O.orderID = OD.orderID " +
                    "WHERE " +
                    "O.orderDate >= '" + start + "' AND O.orderDate <= '"+ end +"' " +
                    "GROUP BY " +
                    "O.orderID, O.orderDate " +
                    ") AS SubQuery " +
                    "GROUP BY " +
                    "FORMAT(ODate, 'dd/MM/yy') " +
                    "ORDER BY " +
                    "FORMAT(ODate, 'dd/MM/yy') DESC " +
                    "OFFSET 0 ROWS FETCH NEXT 31 ROWS ONLY;";
            PreparedStatement p = ConnectDB.getConnectDB_H().prepareStatement(sql);
            ResultSet r = p.executeQuery();
            while (r.next()) {
                String day = r.getString("Day");
                double all = r.getDouble("TotalAmount");
                double revenueMedicine = r.getDouble("M");
                double revenueMedicalS = r.getDouble("S");;
                double revenueFunctionalFood = r.getDouble("F");;
                modelList.add(new ModelDataRS(day, all, revenueMedicine, revenueMedicalS, revenueFunctionalFood));
            }
            r.close();
            p.close();
        } catch (Exception e) {
        }
        return modelList;
    }

    /**
     * Lấy thông tin tổng quan
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public ArrayList<Double> getOverviewStatistical(LocalDate startDate, LocalDate endDate) {
        ArrayList<Double> info = new ArrayList<>();
        int orderCount = 0;
        double totalRevenue = 0.0;
        int totalQuantitySold = 0;

        String sql = "SELECT COUNT(DISTINCT O.orderID) AS OrderCount, " +
                "SUM(OD.lineTotal) AS TotalRevenue, " +
                "SUM(OD.orderQuantity) AS TotalQuantity " +
                "FROM Orders AS O " +
                "INNER JOIN OrderDetail AS OD ON O.orderID = OD.orderID " +
                "WHERE O.orderDate >= ? AND O.orderDate <= ?";

        try (Connection connection = ConnectDB.getConnectDB_H();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    orderCount = resultSet.getInt("OrderCount");
                    totalRevenue = resultSet.getDouble("TotalRevenue");
                    totalQuantitySold = resultSet.getInt("TotalQuantity");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        info.add((double) orderCount);
        info.add(totalRevenue);
        info.add((double) totalQuantitySold);
        return info;
    }

    /**
     * Lấy tỷ lệ sản phẩm đã bán, tính bằng cách lấy tổng sản phẩm đã bán chia cho tổng của tồn kho với sản phẩm đã bán
     *
     * @return
     */
    public double getTotalProductsSold() {
        int totalInStock = 0;
        double pSold = 0.0;
        String sqlInStock = "SELECT " +
                "SUM(CAST(SUBSTRING(SUBSTRING(unitNote, 1, CHARINDEX(')', unitNote)), " +
                "CHARINDEX('(', unitNote) + 1, " +
                "CHARINDEX(')', unitNote) - CHARINDEX('(', unitNote) - 1) AS INT)) AS TotalValue " +
                "FROM Product p";
        try (Connection connection = ConnectDB.getConnectDB_H();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlInStock);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                totalInStock = resultSet.getInt("TotalValue");
            }

            //Lấy tổng sản phẩm đã bán, lấy đơn vị lớn nhất khi nhập
            String sqlSelectAll = "SELECT p.productID, u.unitID, u.name AS nameUnit, SUM(orderQuantity) AS unitSold, unitNote " +
                    "FROM OrderDetail od INNER JOIN Unit u ON od.unitID = u.unitID " +
                    "INNER JOIN Product p ON od.productID = p.productID " +
                    "WHERE unitNote IS NOT NULL " +
                    "GROUP BY p.productID, u.unitID, unitNote, u.name";

            PreparedStatement preparedStatementSelectAll = connection.prepareStatement(sqlSelectAll);
            ResultSet rsSelectAll = preparedStatementSelectAll.executeQuery();


            while(rsSelectAll.next()){
                String nameUnit = rsSelectAll.getString("nameUnit");
                int quantity = rsSelectAll.getInt("unitSold");
                String unitNote = rsSelectAll.getString("unitNote");




                String[] parts = unitNote.split(",\\s*");
                int unitTL = 1;
                int[] quantityTemp;
                boolean isFirstUnit = true;

                for (String part : parts) {//BOX(223), BLISTER_PACK(10), PILL(6)
                    Pattern pattern = Pattern.compile("([A-Z ]+)(?:\\((\\d+)\\))?");
                    Matcher matcher = pattern.matcher(part);
                    if (matcher.matches()) {
                        String enumName = matcher.group(1).trim();
                        int multiplier = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 1;
                        if (isFirstUnit) {
                            isFirstUnit = false;
                            continue;
                        }
                        if(enumName.equals(nameUnit)){
                            pSold += (double) quantity /multiplier;
                            break;
                        }else {
                            pSold += (double) quantity /multiplier;
                        }
                    }
                }



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //lấy tổng số lượng bán(theo đơn vị nhập) chia cho số lượng nhập của toàn bộ sản phẩm * 100
        return (pSold / totalInStock) * 100;
    }

    /**
     * Lấy tổng doanh thu đã bán
     *
     * @return
     */

    public double getRevenueSoldPercentage() {
        double totalDaBan = 0.0;
        double totalThuNhapTonKho = 0.0;
        double phanTram = 0.0;
        String sqlTotalRevenue = "SELECT SUM(totalDue) AS TotalRevenue FROM Orders";
        //TODO: tính trung bình doanh thu tồn kho để giảm mức độ sai số có thể
        String sqlTotalStockValue = "SELECT SUM(AvgRevenue) AS TotalStockValue " +
                                    "FROM ( " +
                                    "    SELECT productID, (SUM(inStock * sellPrice) / COUNT(unitID)) AS AvgRevenue " +
                                    "    FROM ProductUnit " +
                                    "    GROUP BY productID " +
                                    ") AS DoanhThuTon";
        try (Connection connection = ConnectDB.getConnectDB_H();
             PreparedStatement stmtTotalRevenue = connection.prepareStatement(sqlTotalRevenue);
             PreparedStatement stmtTotalStockValue = connection.prepareStatement(sqlTotalStockValue)) {
            try (ResultSet resultSetRevenue = stmtTotalRevenue.executeQuery()) {
                if (resultSetRevenue.next()) {
                    totalDaBan = resultSetRevenue.getDouble("TotalRevenue");
                }
            }
            try (ResultSet resultSetStock = stmtTotalStockValue.executeQuery()) {
                if (resultSetStock.next()) {
                    totalThuNhapTonKho = resultSetStock.getDouble("TotalStockValue");
                }
            }
            if (totalDaBan + totalThuNhapTonKho != 0) { // Kiểm tra để tránh chia cho 0
                phanTram = (totalDaBan / (totalDaBan + totalThuNhapTonKho)) * 100;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return phanTram;
    }

    /**
     * Tỷ lợi lợi nhuận, xem công thức ở tài liệu số 6
     *
     * @return
     */

    //TODO: Fix
    public double getProfit() {
        double loiNhuanDaBan = 0.0;
        double loiNhuanTon = 0.0;
        double tyLeLoiNhuan = 0.0;
        String sqlloiNhuanDaBan = "SELECT p.productID, purchasePrice, unitNote, orderQuantity, lineTotal, u.name " +
                "FROM Product p " +
                "INNER JOIN OrderDetail od ON p.productID = od.productID " +
                "INNER JOIN Unit u ON od.unitID = u.unitID " +
                "WHERE unitNote IS NOT NULL";

        String sqlLoiNhuanTon =
                "SELECT SUM(totalSellPrice) AS totalSellPrice " +
                        "FROM ( " +
                        "    SELECT pu.sellPrice AS totalSellPrice " +
                        "    FROM Product p " +
                        "    INNER JOIN ProductUnit pu ON p.productID = pu.productID " +
                        "    INNER JOIN Unit u ON pu.unitID = u.unitID " +
                        "    WHERE u.name LIKE LEFT(p.unitNote, CHARINDEX('(', p.unitNote + '(') - 1) " +
                        ") AS CalSellPrice";

        try (Connection connection = ConnectDB.getConnectDB_H()) {
            PreparedStatement stmtloiNhuanDaBan = connection.prepareStatement(sqlloiNhuanDaBan);
            ResultSet rsLoiNhuanDaBan = stmtloiNhuanDaBan.executeQuery();

            while (rsLoiNhuanDaBan.next()) {
                double purchasePrice = rsLoiNhuanDaBan.getDouble("purchasePrice");
                String unitNote = rsLoiNhuanDaBan.getString("unitNote");
                int orderQuantity = rsLoiNhuanDaBan.getInt("orderQuantity");
                double lineTotal = rsLoiNhuanDaBan.getDouble("lineTotal");
                String nameUnit = rsLoiNhuanDaBan.getString("name");

                String[] parts = unitNote.split(",\\s*");

                for (String part : parts) {
                    Pattern pattern = Pattern.compile("([A-Z ]+)(?:\\((\\d+)\\))?");
                    Matcher matcher = pattern.matcher(part);

                    if (matcher.matches()) {
                        String enumName = matcher.group(1).trim();
                        int multiplier = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 1;
                        if (enumName.equals(nameUnit)) {
                            loiNhuanDaBan += lineTotal - (purchasePrice / multiplier);
                            break;
                        }
                    }
                }
            }
            //Tính lợi nhuận tồn
            PreparedStatement stmtloiNhuanTon = connection.prepareStatement(sqlLoiNhuanTon);
            ResultSet rsLoiNhuanTon = stmtloiNhuanTon.executeQuery();
            while (rsLoiNhuanTon.next()) {
                loiNhuanTon += rsLoiNhuanTon.getDouble("totalSellPrice");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if ((loiNhuanDaBan + loiNhuanTon) != 0) {
            tyLeLoiNhuan = (loiNhuanDaBan / (loiNhuanDaBan + loiNhuanTon)) * 100;
        }
        return tyLeLoiNhuan;
    }

    /**
     * Kiểm tra hóa đơn có tồn tại hay không
     * @param orderID
     * @return
     */
    public boolean orderIsExists(String orderID) {
        String sql = "SELECT 1 FROM Orders WHERE orderID = ?";
        try (PreparedStatement p = ConnectDB.getConnectDB_H().prepareStatement(sql)) {
            p.setString(1, orderID);
            try (ResultSet r = p.executeQuery()) {
                return r.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        ConnectDB.getInstance().connect();
        Order_DAO order_dao =new Order_DAO();
//        for(ModelDataPS md : Order_DAO.getInstance().getProductStatistical("2024-01-01", "2024-05-05")){
//            System.out.println(md);
//        }
//        new Order_DAO().getOrderByCriterious("Duyên").forEach(System.out::println);
//        new Order_DAO().getOrderList().forEach(System.out::println);
        //order_dao.getOrderByCriterious("OR3010240903003").forEach(x -> System.out.println(x));
        //order_dao.getProductStatistical("2024-01-01", "2024-12-13").forEach(x -> System.out.println(x));
    }



}
