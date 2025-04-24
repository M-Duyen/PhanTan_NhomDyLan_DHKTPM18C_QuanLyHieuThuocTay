package dao;

import model.Employee;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;
import service.EmployeeService;

import java.sql.SQLException;
import java.util.Map;

public class EmployeeDAO extends GenericDAO<Employee, String> implements EmployeeService {
    public EmployeeDAO(Class<Employee> clazz) {
        super(clazz);
    }

    public EmployeeDAO(EntityManager em, Class<Employee> clazz) {
        super(em, clazz);
    }

    public static Employee createSampleEmployee(Faker faker) {
        Employee employee = new Employee();
        employee.setEmployeeID("EP" + faker.number().digits(3));
        employee.setEmployeeName(faker.name().fullName());
        return employee;
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public Employee getListEmployeeByAccountID(String username) {
        return null;
    }

    /**
     * Tạo mã nhân viên tự động
     *
     * @param phone
     * @return
     * @throws SQLException
     */
    @Override
    public String createEmployeeID(String phone) {
        String prefix = "EP";
        String endphone = phone.substring(phone.length() - 2);
        String likePattern = prefix + endphone + "%";

        // Truy vấn tìm giá trị lớn nhất của 2 số cuối sau prefix + endphone
        String jpql = "SELECT MAX(CAST(SUBSTRING(e.employeeID, 5, 2) AS integer)) FROM Employee e WHERE e.employeeID LIKE :pattern";

        Integer max = em.createQuery(jpql, Integer.class)
                .setParameter("pattern", likePattern)
                .getSingleResult();

        int currentMax = (max != null) ? max : 0;
        int nextEmployeeID = currentMax + 1;

        String newEmployeeID = prefix + endphone + String.format("%02d", nextEmployeeID);
        return newEmployeeID;
    }

    /**
     * Lưu danh sách Emp vào map
     *
     * @return
     */
    @Override
    public Map<String, Employee> getAllEmployeesAsMap() {
        return null;
    }

}
