package dao;

import database.ConnectDB;
import entity.Account;
import entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.sql.Date.valueOf;

public class Employee_DAO {
    public Employee_DAO() {
    }

    public static Employee_DAO getInstance() {
        return new Employee_DAO();
    }

    /**
     * Lấy danh sách tất cả nhân viên
     *
     * @return listEmp : ArrayList<Employee>
     */
    public ArrayList<Employee> getListEmployee() {
        Connection con = ConnectDB.getConnection();
        ArrayList<Employee> empList = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM Employee");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String empID = rs.getString(1);
                String empName = rs.getString(2);
                String phoneNumber = rs.getString(3);
                LocalDate brithDate = rs.getDate(4).toLocalDate();
                boolean sex = rs.getBoolean(5);
                String degree = rs.getString(6);
                String mail = rs.getString(7);
                String addr = rs.getString(8);
                boolean status = rs.getBoolean(9);

                Employee emp = new Employee(empID, empName, phoneNumber, brithDate, sex, mail, addr, status, degree);
                empList.add(emp);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empList;

    }

    /**
     *
     * @param username
     * @return
     */
    public Employee getListEmployeeByAccountID(String username) {
        Connection con = ConnectDB.getConnection();
        Employee empl = null;
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("SELECT * FROM Employee E JOIN Account A ON E.employeeID = A.employeeID " +
                    "WHERE accountID = ?");
            sm.setString(1, username);
            ResultSet rs = sm.executeQuery();

            if (rs.next()) {
                String empID = rs.getString(1);
                String empName = rs.getString(2);
                String phoneNumber = rs.getString(3);
                LocalDate brithDate = rs.getDate(4).toLocalDate();
                boolean sex = rs.getBoolean(5);
                String degree = rs.getString(6);
                String mail = rs.getString(7);
                String addr = rs.getString(8);
                boolean status = rs.getBoolean(9);

                empl = new Employee(empName, empID, phoneNumber, brithDate, sex, mail, addr, status, degree);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return empl;
    }

    /**
     * Lọc nhân viên dựa theo mã nhân viên
     *
     * @param emplID
     * @return
     */
    public Employee getEmployee_ByID(String emplID) {
        Connection con = ConnectDB.getConnectDB_H();
        Employee empl = null;
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM Employee WHERE employeeID = ?");
            stmt.setString(1, emplID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String empName = rs.getString(2);
                String phoneNumber = rs.getString(3);
                LocalDate brithDate = rs.getDate(4).toLocalDate();
                boolean sex = rs.getBoolean(5);
                String degree = rs.getString(6);
                String mail = rs.getString(7);
                String addr = rs.getString(8);
                boolean status = rs.getBoolean(9);

                empl = new Employee(emplID, empName, phoneNumber, brithDate, sex, mail, addr, status, degree);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return empl;

    }

    /**
     * Thêm nhân viên
     *
     * @param employee
     * @return
     */
    public boolean addEmployee(Employee employee) {
        Connection con = ConnectDB.getInstance().getConnection();
        String sql = "INSERT INTO Employee(employeeID, employeeName, phoneNumber, birthDate, gender, degree, email,address,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int n = 0;
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, employee.getEmployeeID());
            stmt.setString(2, employee.getEmployeeName());
            stmt.setString(3, employee.getPhoneNumber());
            stmt.setDate(4, valueOf(employee.getBirthDate()));
            stmt.setBoolean(5, employee.isGender());
            stmt.setString(6, employee.getDegree());
            stmt.setString(7, employee.getEmail());
            stmt.setString(8, employee.getAddress());
            stmt.setBoolean(9, employee.isStatus());

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }


    /**
     * Cập nhật thông tin nhân viên
     *
     * @param employee
     * @return
     */
    public boolean updateEmployee(Employee employee) {
        Connection con = ConnectDB.getInstance().getConnection();
        String sql = "UPDATE Employee SET employeeName = ?, phoneNumber = ?, birthDate = ?, gender = ?, degree = ?, email = ?, address = ?, status = ?  WHERE employeeID = ?";
        int n = 0;
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, employee.getEmployeeName());
            stmt.setString(2, employee.getPhoneNumber());
            stmt.setDate(3, valueOf(employee.getBirthDate()));
            stmt.setBoolean(4, employee.isGender());
            stmt.setString(5, employee.getDegree());
            stmt.setString(6, employee.getEmail());
            stmt.setString(7, employee.getAddress());
            stmt.setBoolean(8, employee.isStatus());
            stmt.setString(9, employee.getEmployeeID());


            n = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return n > 0;
    }

    /**
     * Lọc nhân viên theo tiêu chí bất kỳ
     *
     * @param criterious
     * @return empByCriList : ArrayList<Employee>
     */
    public ArrayList<Employee> getEmployeeByCriterious(String criterious) {

        ArrayList<Employee> empByCriList = new ArrayList<>();
        ArrayList<Employee> empList = getListEmployee();

        for (Employee emp : empList) {
            if (emp.getEmployeeID().toLowerCase().contains(criterious.toLowerCase()) ||
                    emp.getEmployeeName().toLowerCase().contains(criterious.toLowerCase()) ||
                    emp.getPhoneNumber().contains(criterious.toLowerCase())) {
                empByCriList.add(emp);
            }
        }
        return empByCriList;
    }

    /**
     * Tạo mã nhân viên tự động
     *
     * @param phone
     * @return
     * @throws SQLException
     */
    public String createEmployeeID(String phone) throws SQLException {
        Connection con = ConnectDB.getConnection();
        String newEmployeeID = null;
        String prefix = "EP";
        String endphone = phone.substring(phone.length() - 2);
        String query = "SELECT MAX(CAST(SUBSTRING(employeeID, 5, 2) AS INT)) AS maxEmployeeID " +
                "FROM Employee " +
                "WHERE SUBSTRING(employeeID, 1, 4) = ?";
        PreparedStatement sm = null;
        try {
            sm = con.prepareStatement(query);
            sm.setString(1, prefix + endphone);

            ResultSet resultSet = sm.executeQuery();
            int currentMax = 0;
            if (resultSet.next() && resultSet.getObject("maxEmployeeID") != null) {
                currentMax = resultSet.getInt("maxEmployeeID");
                System.out.println("Mã lớn nhất hiện tại: " + currentMax);
            }

            int nextEmployeeID = currentMax + 1;

            newEmployeeID = prefix + endphone + String.format("%02d", nextEmployeeID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newEmployeeID;
    }

    /**
     * lưu danh sách Emp vào map
     *
     * @return
     */

    public Map<String, Employee> getAllEmployeesAsMap() {
        Connection con = ConnectDB.getConnection();
        Map<String, Employee> employeeMap = new HashMap<>();


        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM Employee");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String empID = rs.getString(1);
                String empName = rs.getString(2);
                String phoneNumber = rs.getString(3);
                LocalDate brithDate = rs.getDate(4).toLocalDate();
                boolean sex = rs.getBoolean(5);
                String degree = rs.getString(6);
                String mail = rs.getString(7);
                String addr = rs.getString(8);
                boolean status = rs.getBoolean(9);

                Employee emp = new Employee(empID, empName, phoneNumber, brithDate, sex, mail, addr, status, degree);
                employeeMap.put(empID, emp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employeeMap;
    }


    public static void main(String[] args) throws SQLException {
        ConnectDB.getInstance().connect();
        Employee_DAO emp_dao = new Employee_DAO();
        System.out.println(emp_dao.getEmployee_ByID("EP1501"));
        Map<String, Employee> employeeMap = emp_dao.getAllEmployeesAsMap();

        // In ra danh sách nhân viên
        for (Map.Entry<String, Employee> entry : employeeMap.entrySet()) {
            String empID = entry.getKey();
            Employee emp = entry.getValue();

            System.out.println("ID: " + empID);
            System.out.println("Name: " + emp.getEmployeeName());
            System.out.println("Phone: " + emp.getPhoneNumber());
            System.out.println("Birth Date: " + emp.getBirthDate());
            System.out.println("Sex: " + (emp.isGender() ? "Nữ" : "Nam"));
            System.out.println("Degree: " + emp.getDegree());
            System.out.println("Email: " + emp.getEmail());
            System.out.println("Address: " + emp.getAddress());
            System.out.println("Status: " + (emp.isStatus() ? "Đang làm" : "Nghỉ làm"));
            System.out.println("---------------------------------------------------");
        }
    }


}
