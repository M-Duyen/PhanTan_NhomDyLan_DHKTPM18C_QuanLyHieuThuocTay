package dao;

import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;
import service.EmployeeService;
import utils.JPAUtil;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
     * Tạo mã nhân viên tự động
     *
     * @param phone
     * @return
     * @throws SQLException
     */
    @Override
    public String createEmployeeID(String phone) {
    EntityManager em = JPAUtil.getEntityManager(); // Hoặc bạn inject EntityManager nếu dùng Spring

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

}
